let uploadedPhoto = null;

function initializeClientPhotoUpload() {

    const $photoInput = $('#clientPhoto');
    const $dropZone = $('#photoDropZone');
    const $placeholder = $('#photoPlaceholder');
    const $preview = $('#clientPhotoPreview');
    const $chooseButton = $('#choosePhotoBtn');
    const $removeButton = $('#removePhotoBtn');
    const $profileImageUrl = $('#profileImageUrl');

    /* Choose photo */
    $chooseButton.add($dropZone).on('click', function(event) {
        if ($(event.target).closest('#removePhotoBtn').length) return;
        if ($(event.target).is('#clientPhoto')) return;

        event.preventDefault();
        $photoInput.trigger('click');
    });

    /* File selection */
    $photoInput.on('change', async function() {
        const file = this.files[0];
        if (file) await processClientPhoto(file);
    });

    /* Drag and drop */
    $dropZone.on({
        dragover: function(event) {
            event.preventDefault();
            event.originalEvent.dataTransfer.dropEffect = 'copy';
            $dropZone.addClass('drag-over');
        },

        dragleave: function(event) {
            event.preventDefault();
            $dropZone.removeClass('drag-over');
        },

        drop: async function(event) {
            event.preventDefault();
            $dropZone.removeClass('drag-over');

            const file = event.originalEvent.dataTransfer.files[0];
            if (!file) return;

            try {
                const dataTransfer = new DataTransfer();
                dataTransfer.items.add(file);
                $photoInput[0].files = dataTransfer.files;
            } catch (error) {
                console.warn('Unable to assign dropped file.', error);
            }

            await processClientPhoto(file);
        }
    });

    /* Remove photo */
    $removeButton.on('click', async function(event) {
        event.preventDefault();
        event.stopPropagation();

        await removeClientPhoto();
    });

    /* Process selected photo */
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

        $profileImageUrl.val(data.secure_url);
        $preview.attr('src', data.secure_url).show();

        $placeholder.hide();
        $chooseButton.hide();
        $removeButton.css('display', 'flex');
    }

    /* Validate photo */
    function validateClientPhoto(file) {

        if (!['image/jpeg', 'image/png'].includes(file.type)) {
            showErrorPopup('Error', 'Please select a valid JPG or PNG image.');
            return false;
        }

        if (file.size > 1024 * 1024) {
            showErrorPopup('Error', 'The maximum allowed image size is 1 MB.');
            return false;
        }

        return true;
    }

    /* Upload photo */
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
                showErrorPopup("Error", data.message || 'Photo upload failed.');
            }

            return data;
        } catch (error) {
            console.error('Photo upload error:', error);
            showErrorPopup('Error', 'Photo upload failed. Please try again.');
            return null;
        } finally {
            setPhotoUploadingState(false);
        }
    }

    /* Delete photo from Cloudinary */
    async function deleteClientPhotoFromCloudinary(publicId) {

        if (!publicId) return false;

        try {
            const response = await fetch(
                `/api/v1/cloudinary/delete-image?publicId=${encodeURIComponent(publicId)}`,
                { method: 'DELETE' }
            );

            const data = await response.json();

            if (!response.ok || data.result !== 'ok') {
                showErrorPopup("Error", data.message || 'Cloudinary deletion failed.');
            }

            return true;
        } catch (error) {
            console.error('Photo deletion error:', error);
            return false;
        }
    }

    /* Remove photo */
    async function removeClientPhoto() {

        if (uploadedPhoto?.public_id) {

            $removeButton.prop('disabled', true);

            const deleted = await deleteClientPhotoFromCloudinary(
                uploadedPhoto.public_id
            );

            if (!deleted) {
                $removeButton.prop('disabled', false);
                showErrorPopup('Error', 'Unable to remove the photo. Please try again.');
                return;
            }
        }

        resetClientPhoto();
    }

    /* Reset photo UI */
    function resetClientPhoto() {

        uploadedPhoto = null;

        $photoInput.val('');
        $profileImageUrl.val('');
        $preview.attr('src', '').hide();

        $placeholder.show();
        $chooseButton.show();
        $removeButton.hide();
        $dropZone.removeClass('drag-over');

        setPhotoUploadingState(false);
    }

    /* Uploading state */
    function setPhotoUploadingState(isUploading) {

        $chooseButton
            .prop('disabled', isUploading)
            .toggleClass('uploading', isUploading)
            .html(isUploading
                ? '<i class="fa-solid fa-spinner fa-spin"></i> Uploading...'
                : '<i class="fa-solid fa-cloud-arrow-up"></i> Choose File'
            );

        $removeButton.prop('disabled', isUploading);
    }
}