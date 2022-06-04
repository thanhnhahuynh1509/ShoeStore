function changeImageInput(fileUpload) {
    const file = fileUpload.files[0];
    const reader = new FileReader();

    reader.onload = (e) => {
        $("#imagePreview").attr("src", e.target.result);
    }

    reader.readAsDataURL(file);
}

const btnDelete = $("#btn-delete");

function changeStatus(btn) {
    let id = btn.attr("data-id");
    let status = btn.attr("data-status");
    let param = {
        id: id,
        status: status
    }

    $.get(urlChangeStatus, param, function(response) {
        btn.attr("data-status", response);
        let icon = btn.children("i");
        if(icon.hasClass('text-secondary')) {
            icon.removeClass('text-secondary');
            icon.removeClass('bx-checkbox');
            icon.addClass('text-success');
            icon.addClass('bx-checkbox-square');
        } else {
            icon.addClass('text-secondary');
            icon.addClass('bx-checkbox');
            icon.removeClass('text-success');
            icon.removeClass('bx-checkbox-square');
        }
        showToastAlert('Cập nhật trạng thái thành công!', 'success');
    }).fail(function() {
        alert("fail!");
    })
}

function confirmDelete(btn) {
    let id = btn.attr("data-id");
    showModalConfimDialog('Xác nhận xóa', 'Bạn có chắc chắn muốn xóa dữ liệu với ID: ' + id);
    btnDelete.on('click', function () {
        $.get(urlDelete, {id: id}, function(response) {
            if(response === "OK") {
                btn.closest("tr").remove();
                showToastAlert('Xóa dữ liệu thành công!', 'success');
            } else {
                showToastAlert('Xóa dữ liệu không thành công!', 'danger');
            }
        }).fail(function() {
            showToastAlert('Lỗi hệ thống!', 'danger');
        })
        btnDelete.off('click')
        modalConfirmDialog.modal('hide');
    })
}