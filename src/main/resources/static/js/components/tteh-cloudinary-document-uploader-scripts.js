let uploadRequest;
/**
 * =========================================================
 * PROGRESS REPORT FILE UPLOAD
 * =========================================================
 */
function initializeDocumentUpload() {

    const $fileInput = $('#progressReportFile');
    const $dropZone = $('#fileDropZone');
    const $placeholder = $('#uploadPlaceholder');

    const $selectedFileContainer = $('#selectedFileContainer');
    const $selectedFileName = $('#selectedFileName');
    const $selectedFileSize = $('#selectedFileSize');

    const $chooseFileBtn = $('#chooseFileBtn');
    const $removeFileBtn = $('#removeFileBtn');

    /**
     * =====================================================
     * CHOOSE FILE BUTTON
     * =====================================================
     */
    $chooseFileBtn.on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();
        $fileInput.trigger('click');
    });

    /**
     * =====================================================
     * DROP ZONE CLICK
     * =====================================================
     */
    $dropZone.on('click', function (event) {
        if ($(event.target).is('#progressReportFile')
            || $(event.target).closest('button').length) {
            return;
        }

        $fileInput.trigger('click');
    });

    /**
     * =====================================================
     * FILE INPUT CHANGE
     * =====================================================
     */
    $fileInput.on('change', async function (event) {
        const file = event.target.files[0];
        if (!file) { return; }
        await processDocument(file);
    });

    $dropZone.on('dragover', function (event) {
        event.preventDefault();
        event.originalEvent.dataTransfer.dropEffect = 'copy';
        $dropZone.addClass('drag-over');
    });

    $dropZone.on('dragleave', function (event) {
        event.preventDefault();
        $dropZone.removeClass('drag-over');
    });


    /**
     * =====================================================
     * DROP FILE
     * =====================================================
     */
    $dropZone.on('drop', async function (event) {
        event.preventDefault();
        $dropZone.removeClass('drag-over');
        const files = event.originalEvent.dataTransfer.files;

        if (!files || files.length === 0) {
            return;
        }

        const file = files[0];

        try {
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            $fileInput[0].files = dataTransfer.files;
        } catch (error) {
            console.warn('Unable to assign dropped file to input.', error);
        }

        await processDocument(file);
    });


    /**
     * =====================================================
     * REMOVE FILE
     * =====================================================
     */
    $removeFileBtn.on('click', function (event) {
        console.log("Click Remove File!")
        event.preventDefault();
        event.stopPropagation();
        removeDocument();
    });


    /**
     * =====================================================
     * PROCESS DOCUMENT
     * =====================================================
     */
    async function processDocument(file) {

        if (!validateDocument(file)) {
            $fileInput.val('');
            return;
        }

        displaySelectedFile(file);
        setUploadingState(true);

        const data = await uploadDocumentToCloudinary(file);
        console.log(data);

        if (!data) {
            removeDocument();
            return;
        }

        $selectedFileName.text(`${data.original_filename}.${data.format}`);
        $selectedFileSize.text(`${data.size_mb} MB`);

        setUploadingState(false);
    }

    /**
     * =====================================================
     * VALIDATE DOCUMENT
     * =====================================================
     */
    function validateDocument(file) {

        const allowedTypes = [
            'application/pdf',
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        ];

        /* Validate file type */
        if (!allowedTypes.includes(file.type)) {
            showErrorPopup('Error','Please select a valid PDF, DOCX, or XLSX file.');
            return false;
        }

        /* Maximum file size: 5 MB */
        const maxSize = 5 * 1024 * 1024;

        if (file.size > maxSize) {
            showErrorPopup('Error', 'The maximum allowed file size is 5 MB.');
            return false;
        }

        return true;
    }

    /**
     * =====================================================
     * UPLOAD TO CLOUDINARY
     * =====================================================
     */
    async function uploadDocumentToCloudinary(file) {

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('/api/v1/cloudinary/upload', {
                method: 'POST',
                body: formData
            });

            const data = await response.json();

            if (!response.ok) {
                showErrorPopup('Error', 'Document upload failed.');
            }

            uploadRequest = data;

            $("#fileDropZone").removeClass("has-error");

            return data;
        } catch (error) {
            console.error('Document upload error:', error);
            showErrorPopup('Error', error.message || 'Document upload failed. Please try again.');
            return null;
        }
    }

    async function deleteDocumentFromCloudinary(publicId) {

        try {
            const response = await fetch(
                `/api/v1/cloudinary/delete?publicId=${encodeURIComponent(publicId)}`,
                {
                    method: 'DELETE'
                }
            );

            const data = await response.json();

            if (!response.ok) {
                showErrorPopup('Error', 'Document deletion failed.');
            }

            return data.result === 'ok';

        } catch (error) {
            console.error('Document deletion error:', error);
            return false;
        }
    }


    async function generateCloudinarySignature(stringToSign) {
        const apiSecret = 'U4IC3ORH4Pla3qIRbN1gxrp2VHQ';
        return CryptoJS.SHA1(stringToSign + apiSecret).toString(CryptoJS.enc.Hex);
    }

    /**
     * =====================================================
     * DISPLAY SELECTED FILE
     * =====================================================
     */
    function displaySelectedFile(file) {

        const sizeMB = (file.size / (1024 * 1024)).toFixed(2);

        $selectedFileName.text(file.name);
        $selectedFileSize.text(`${sizeMB} MB`);
        $selectedFileContainer.show();

        $placeholder.hide();
        $chooseFileBtn.hide();
        $removeFileBtn.css('display', 'flex');
    }

    /**
     * =====================================================
     * REMOVE DOCUMENT
     * =====================================================
     */
    function removeDocument() {
        const resumeUrl = $('#selectedFileName a').attr('href');
        console.log("resumeUrl : " + resumeUrl);

        let publicId = uploadRequest?.public_id
            || (resumeUrl
                ? resumeUrl.split('/upload/')[1]
                    ?.replace(/^v\d+\//, '')
                    .replace(/\.[^/.]+$/, '')
                : null);

        if (publicId && !/\.[^/.]+$/.test(publicId)) {
            publicId += '.pdf';
        }

        console.log("public id : " + publicId);

        if (!publicId) {
            console.error('Unable to extract Cloudinary Public ID.');
            return;
        }

        deleteDocumentFromCloudinary(publicId)
            .then(function (deleted) {
                if (deleted) {
                    $fileInput.val('');
                    $selectedFileName.empty();
                    $selectedFileSize.text('');
                    $selectedFileContainer.hide();
                    $placeholder.show();
                    $chooseFileBtn.show();
                    $removeFileBtn.hide();
                    $dropZone.removeClass('drag-over');
                    uploadRequest = null;
                } else {
                    console.error('Cloudinary deletion failed.');
                }
            })
            .catch(function (error) {
                console.error('Cloudinary delete error:', error);
            });
    }

    /**
     * =====================================================
     * UPLOADING STATE
     * =====================================================
     */
    function setUploadingState(isUploading) {

        if (isUploading) {
            $chooseFileBtn
                .prop('disabled', true)
                .addClass('uploading');

            $removeFileBtn
                .prop('disabled', true);

            $chooseFileBtn.html(`
                <i class="fa-solid fa-spinner fa-spin"></i>
                Uploading...
            `);
        } else {
            $chooseFileBtn
                .prop('disabled', false)
                .removeClass('uploading');

            $removeFileBtn
                .prop('disabled', false);

            $chooseFileBtn.html(`
                <i class="fa-solid fa-cloud-arrow-up"></i>
                Choose File
            `);
        }
    }
}