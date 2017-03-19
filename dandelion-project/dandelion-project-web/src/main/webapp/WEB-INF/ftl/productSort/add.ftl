<form id="form" action="/productSort/add" method="post" class="col-md-10 left-margin">
<input type="hidden" name="id" value="${productSort.id}">
    <div >
        <div class="form-row">
            <div class="form-label col-md-2">
                <label for="">
                    	类型名称:
                </label>
            </div>
            <div class="form-input col-md-5">
                <input placeholder="类型名称" type="text" name="name" value="${productSort.name}"  data-required="true" class="parsley-validated" >
            </div>
        </div>
		<div class="form-row">
			<div class="form-label col-md-2">
	            <label for="">类型描述：</label>
	        </div>
	        <div class="form-input col-md-10" >
	            <textarea placeholder="描述详情" name="note"  class="small-textarea" data-required="true" data-rangelength="[1,1000]" class="parsley-validated">${productSort.note}</textarea>
	        </div>
		</div>
        
	</div>
</form>
