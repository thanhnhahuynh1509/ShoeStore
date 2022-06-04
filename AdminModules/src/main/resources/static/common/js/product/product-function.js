function changeExtraImage(input) {
    let file = input.files[0];
    let reader = new FileReader();
    let dataCheckAdded = input.getAttribute("data-check-added");
    input.setAttribute("data-check-added", "true");

    if (dataCheckAdded === "false") {
        input.closest(".doc-example").querySelector("a").classList.remove("hidden");
        $("#container-extra-images").append(extraImage());
    }

    reader.onload = (e) => {
        input.closest(".doc-example").querySelector("input[name='extraImageNames']").value = file.name;
        input.closest(".doc-example").querySelector("img").setAttribute("src", e.target.result);
    }

    reader.readAsDataURL(file);
}

function extraImage() {
    return `<div class="doc-example col-md-4 mb-4">
            <input type="hidden" name="extraImageIds" value=""/>
            <input type="hidden" name="extraImageNames" value=""/>
            <div class="tab-content doc-example-content" data-label="Hình ảnh phụ">
                <a onclick="removeExtraImage($(this))" class="hidden cursor-pointer" style="position: absolute; top: -10px; right: -9px">
                    <i style="font-size: 30px; color: #ff8686;" class='bx bxs-x-circle'></i>
                </a>
                <div class="mb-3">
                    <img src="${urlImageProductDefault}" style="width: 150px"/>
                </div>
                <div class="mb-3">
                    <input onchange="changeExtraImage(this)" data-check-added="false" class="form-control"
                           type="file" name="extraFileUpload">
                </div>
            </div>
        </div>`;
}

function removeExtraImage(btn) {
    btn.closest(".doc-example").remove();
}

function detail() {
    return `<div class="key-value-pair">
      <input type="hidden" value="" name="detailIds"/>
      <div class="row">
        <div class="col-md-5">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" value="" placeholder="Nhập tên chi tiết..." name="detailNames" />
            <label th:for="cost">Tên</label>
          </div>
        </div>
        <div class="col-md-5">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" value="" placeholder="Nhập giá trị chi tiết..." name="detailValues" />
            <label th:for="cost">Giá trị</label>
          </div>
        </div>
        <div class="col-md-2">
          <a onclick="removeDetail($(this))" class="btn btn-secondary btn-sm text-white">
            <i class='bx bx-x'></i>
          </a>
        </div>
      </div>
    </div>`;
}

function addDetail() {
    $(".container-detail").append(detail());
}

function removeDetail(btn) {
    btn.closest(".key-value-pair").remove();
}
