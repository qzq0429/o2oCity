/**
 * 
 */
$(function(){
	var shopId=getQueryString('shopId');
	var isEdit = shopId?true :false;
	var initUrl = '/o2oCity/shopadmin/getshopinitinfo';
	var registerShopUrl ='/o2oCity/shopadmin/registershop';
	var shopInfoUrl='/o2oCity/shopadmin/getshopbyid?shopId='+shopId;
	var editShopUrl='/o2oCity/shopadmin/modifyshop';
    if(isEdit)
    {
      	getShopInfo();
    }else{
	getShopInitInfo();
    }

	function getShopInfo(){
		$.getJSON(shopInfoUrl,function(data){
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var tempHtml ='';
				var tempAreaHtml='';
				data.shopCategoryList.map(function(item,index){
					tempHtml+='<option data-id="'+item.shopCategoryId+'">'
					+item.shopCategoryName+'</option>';
					});
					data.areaList.map(function(item,index){
						tempAreaHtml+= '<option data-id="'+item.areaId+'">'
						+item.areaName+'</option>';
					});
					$('#shop-category').html(tempHtml);
					$('#shop-category').attr('disabled','disabled');
					$('#area').html(tempAreaHtml);
					$("#area option[data-id='"+shop.area.areaId+"']").attr('selected','selected')
			}
		});
	}
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
		
				var tempHtml ='';
				var tempAreaHtml='';
				data.shopCategoryList.map(function(item,index){
					tempHtml+='<option data-id="'+item.shopCategoryId+'">'
					+item.shopCategoryName+'</option>';
					});
					data.areaList.map(function(item,index){
						tempAreaHtml+= '<option data-id="'+item.areaId+'">'
						+item.areaName+'</option>';
					});
					$('#shop-category').html(tempHtml);
					$('#area').html(tempAreaHtml);
			}
		});
	}
		$('#submit').click(function(){
			var shop = {};
			if(isEdit){
				shop.shopId=shopId;
			}
			shop.shopName = $('#shop-name').val();
			shop.shopAddr = $('#shop-addr').val();
			shop.phone = $('#shop-phone').val();
			shop.shopDesc = $('#shop-desc').val();
			shop.shopCategory={
					shopCategoryId:$('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			shop.area={
					areaId:$('#area').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			var shopImg = $('#shop-img')[0].files[0];
			var formData = new FormData();
			formData.append("shopImg",shopImg);
			formData.append("name","qzq");
			formData.append("shopStr",JSON.stringify(shop));
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual){
				$.toast('请输入验证码');
				return;
			}
			formData.append('verifyCodeActual',verifyCodeActual);
			console.log(formData.get("name"));
			$.ajax({
				url:isEdit?editShopUrl:registerShopUrl,
				type:'POST',
				data:formData,
				processData:false,
				contentType:false,
				cache:false,
				success:function(data) {
					if (data.success) {
						$.toast('提交成功！');
						$('#captcha_img').click();
					} else {
						$.toast('提交失败！');
						$.toast(data.errMsg);
					$('#captcha_img').click();
					}
					
				}
			});
		
		});
	

});