/**
 * 
 */
$(function() {
	var productId = getQueryString('productId');
	var infoUrl = '/o2oCity/shopadmin/getproductbyid?productId=' + productId;
	var categoryUrl = '/o2oCity/shopadmin/getproductcategorylist';
	var productPostUrl = '/o2oCity/shopadmin/modifyproduct';
	var isEdit = false;
	if (productId) {
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2oCity/shopadmin/addproduct';
	}

	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {

							if (data.success) {
								var product = data.product;
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.Desc);
								$('#priority').val(product.priority);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(
										product.promotionPrice);
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;
								;
								optionArr
										.map(function(item, index) {
											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';

										});
								$('#category').html(optionHtml);
							}
						});
	}
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}

	});

	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';

				});
				$('#category').html(optionHtml);
			}
		});
	}
	$('#submit').click(
			function() {
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				product.productCategory = {
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				product.productId = productId;
				var thumbnail = $('#small-img')[0].files[0];
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				$('.detail-img').map(
						function(index, item) {
							if ($('.detail-img')[index].files.length > 0) {

								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}

						});
				formData.append('productStr', JSON.stringify(product));
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码!');
					return;
				}
				formData.append('verifyCodeActual', verifyCodeActual);
				
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data:formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
                       if(data.success){
                    	   $.toast('提交成功!');
                    	   $('#captcha_img').click();
                       }else{
                    	   $.toast('提交失败！');
                    	   $.toast(data.errMsg);
                    	   $('#captcha_img').click();
                       }
					}

				});
			});
})