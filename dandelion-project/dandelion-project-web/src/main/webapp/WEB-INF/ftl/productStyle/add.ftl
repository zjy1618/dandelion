<form id="form" action="/productStyle/add" method="post" class="col-md-10 left-margin">
<input type="hidden" name="productCode" value="${product.productCode}">
    <div >
        <div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    	款式名称:
                </label>
            </div>
            <div class="form-input col-md-5">
                <input placeholder="款式名称" type="text" name="name" value="${productStyle.name}"  data-required="true" class="parsley-validated" >
            </div>
        </div>
        <div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    单价金额:
                </label>
            </div>
            <div class="form-input col-md-5">
                <input placeholder="单价金额" type="text" name="unitAmount" value="${productStyle.unitAmount}"  data-required="true" class="parsley-validated" data-type="number">
            </div>
        </div>
		<div class="form-row">
			<div class="form-label col-md-2">
	            <label for="">单位名称：</label>
	        </div>
            <div class="form-input col-md-5">
                <input placeholder="单位名称" type="text" name="unitName" value="${productStyle.unitName}"  data-required="true" class="parsley-validated" >
            </div>
		</div>
	</div>
</form>
