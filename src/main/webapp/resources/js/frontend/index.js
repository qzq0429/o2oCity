/**
 * 
 */

$(function(){
	
	var url = '/o2oCity/frontend/listmainpageinfo';
	$.getJSON(url,function(data){
		if(data.success){
			var headLineList = data.headLineList;
			var swiperHtml = '';
			headLineList.map(function(item,index){
				swiperHtml += ''+'<div class="swiper-slide img-wrap">'
				      +'<a href="'+item.lineLink
				      +'" external><img class="banner-img" src="'+item.lineImg
				      +'" alt="'+item.lineName+'"></a>'+'</div>';
				
			});
			$('.swiper-wrapper').html(swiperHtml);
			$('.swiper-container').swiper({
				autoplay:2000,
				autoplayDisableOnInteraction:false
				
			});
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml ='';
			shopCategoryList.map(function(item,index){
				categoryHtml+=''
					+'<div class="col-50 shop-classify" data-category='
					+item.shopCategoryId+'>'+'<div class="word">'
					+'<p class="shop-title"'+item.shopCategoryName
					+'</p>'+'<p class="shop-desc">'
				 	+item.shopCategoryDesc+'</p>'+'</div>'
					+'<div class="shop-classify-img-warp">'
					+'<img class="shop-img" src="'+item.shopCategoryImg
					+'">'+'</div>'+'</div>';
				
					
				
				
			});
			$('.row').html(categoryHtml);
		}
		
		
	});


	
	$('#me').click(function(){
		var ss='/o2oCity/wechatlogin/userInfo'
		$.getJSON(ss,function(data){
			var user = data.user;
			if(user)
			{
			alert(user.name);
			alert(user.profileImg);
			}else{
				alert('null');
			}
			$('#u_name').html(user.name);
			$('#u_img').attr("src",user.profileImg);

			
		});
		$.openPanel('#panel-js-demo');
		
	});
	$('.row').on('click','.shop-classify',function(e){
		var shopCategoryId=e.currentTarget.dataset.category;
		var newUrl = '/o2oCity/frontend/shoplist?parentId='+shopCategoryId;
		window.location.href = newUrl;
	});

})