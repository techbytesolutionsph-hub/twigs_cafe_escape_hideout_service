let uploadedPhoto = null;

function initializeClientPhotoUpload() {

    const $photoInput = $('#clientPhoto');
    const $dropZone = $('#photoDropZone');
    const $placeholder = $('#photoPlaceholder');
    const $preview = $('#clientPhotoPreview');
    const $chooseButton = $('#choosePhotoBtn');
    const $removeButton = $('#removePhotoBtn');
    const $profileImageUrl = $('#profileImageUrl');

    $chooseButton.on('click', function(event) {
        event.preventDefault();
        $photoInput.trigger('click');
    });

    $dropZone.on('click', function(event) {
        if ($(event.target).is('#clientPhoto')) {
            return;
        }

        $photoInput.trigger('click');
    });

    $photoInput.on('change', async function(event) {

        const file = event.target.files[0];

        if (!file) {
            return;
        }

        await processClientPhoto(file);
    });

    $dropZone.on('dragover', function(event) {
        event.preventDefault();
        event.originalEvent.dataTransfer.dropEffect = 'copy';
        $dropZone.addClass('drag-over');
    });

    $dropZone.on('dragleave', function(event) {
        event.preventDefault();
        $dropZone.removeClass('drag-over');
    });

    $dropZone.on('drop', async function(event) {

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
            $photoInput[0].files = dataTransfer.files;

        } catch (error) {
            console.warn('Unable to assign dropped file to input.', error);
        }

        await processClientPhoto(file);
    });

    $removeButton.on('click', async function(event) {
        event.preventDefault();
        event.stopPropagation();

        console.log('Remove Photo clicked');
        console.log('Uploaded Photo:', uploadedPhoto);

        await removeClientPhoto();
    });

    async function processClientPhoto(file) {

        if (!validateClientPhoto(file)) {
            $photoInput.val('');
            return;
        }

        const reader = new FileReader();

        reader.onload = function(event) {
            $preview.attr('src', event.target.result).show();
            $placeholder.hide();
        };

        reader.readAsDataURL(file);
        setPhotoUploadingState(true);

        const data = await uploadClientPhotoToCloudinary(file);

        if (!data) {
            resetClientPhoto();
            return;
        }

        uploadedPhoto = {
            secure_url: data.secure_url,
            public_id: data.public_id
        };

        console.log('Uploaded Photo:', uploadedPhoto);
        console.log('Cloudinary Public ID:', uploadedPhoto.public_id);
        console.log('Cloudinary Secure URL:', uploadedPhoto.secure_url);

        $profileImageUrl.val(data.secure_url);
        $preview.attr('src', data.secure_url).show();
        $placeholder.hide();
        $chooseButton.hide();
        $removeButton.css('display', 'flex');
    }

    function validateClientPhoto(file) {

        const allowedTypes = [
            'image/jpeg',
            'image/png'
        ];

        if (!allowedTypes.includes(file.type)) {
            showErrorPopup('Error','Please select a valid JPG or PNG image.');
            return false;
        }

        const maxSize = 1024 * 1024;

        if (file.size > maxSize) {
            showErrorPopup('Error','The maximum allowed image size is 1 MB.');
            return false;
        }

        return true;
    }

    async function uploadClientPhotoToCloudinary(file) {

        const formData = new FormData();
        formData.append('file', file);

        try {

            const response = await fetch('/api/v1/cloudinary/upload', {
                method: 'POST',
                body: formData
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Photo upload failed.');
            }

            return {
                secure_url: data.secure_url,
                public_id: data.public_id
            };

        } catch (error) {

            console.error('Cloudinary upload error:', error);
            showErrorPopup('Error', 'Photo upload failed. Please try again.');

            return null;

        } finally {
            setPhotoUploadingState(false);
        }
    }

    async function deleteClientPhotoFromCloudinary(publicId) {

        if (!publicId) {
            return false;
        }

        try {
            const response = await fetch(
                `/api/v1/cloudinary/delete-image?publicId=${encodeURIComponent(publicId)}`,
                {
                    method: 'DELETE'
                }
            );

            const data = await response.json();

            console.log('Cloudinary Delete Response:', data);

            if (!response.ok || data.result !== 'ok') {
                throw new Error(data.message || 'Cloudinary deletion failed.');
            }

            return true;

        } catch (error) {
            console.error('Cloudinary photo deletion error:', error);
            return false;
        }
    }

    async function removeClientPhoto() {

        const publicId = uploadedPhoto?.public_id;
        console.log('Removing Cloudinary Photo:', publicId);

        if (publicId) {
            $removeButton.prop('disabled', true);

            const deleted = await deleteClientPhotoFromCloudinary(publicId);

            if (!deleted) {
                $removeButton.prop('disabled', false);
                showErrorPopup('Error','Unable to remove the photo. Please try again.');
                return;
            }

            console.log('Photo successfully deleted from Cloudinary.');
        }

        $photoInput.val('');
        $profileImageUrl.val('');
        $preview.attr('src', '').hide();

        $placeholder.show();
        $chooseButton.show();
        $removeButton.hide();
        $dropZone.removeClass('drag-over');
        uploadedPhoto = null;

        setPhotoUploadingState(false);
    }

    function resetClientPhoto() {

        $photoInput.val('');
        $profileImageUrl.val('');
        $preview.attr('src', '').hide();

        $placeholder.show();
        $chooseButton.show();
        $removeButton.hide();
        $dropZone.removeClass('drag-over');
        uploadedPhoto = null;

        setPhotoUploadingState(false);
    }

    function setPhotoUploadingState(isUploading) {

        if (isUploading) {
            $chooseButton.prop('disabled', true).addClass('uploading');
            $removeButton.prop('disabled', true);
            $chooseButton.html(`<i class="fa-solid fa-spinner fa-spin"></i> Uploading...`);
        } else {
            $chooseButton.prop('disabled', false).removeClass('uploading');
            $removeButton.prop('disabled', false);
            $chooseButton.html(`<i class="fa-solid fa-cloud-arrow-up"></i>Choose File`);
        }
    }
}