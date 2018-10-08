/**
 * 
 */
$(function(){
	var loading = false;
	////分页允许返回的最大条数，超过此数禁止访问后台；
	var maxItems = 999;
	//一页返回的最大条数
	var pageSize = 10;
	//获取店铺列表的url;
	var listUrl = '/o2oCity/frontend/listshops';
	//获取店铺类别列表以及区域列表的URL
	var searchDivurl = '/o2oCity/frontend/listshopspageinfo';
	//页码
	var pageNum = 1;
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName='';
    //渲染出店铺类别列表及区域列表以供搜索

	//addItems(pageSize,pageNum);
	/**
	 * 获取店铺类别列表以及区域列表信息
	 * 
	 * @returns
	 */
	function getSearchDivData(){
		//如果传入了parentId，则去除次一级类别下面的所有二级类别
		var surl = searchDivurl +'?'+'parentId='+parentId ;
		alert(surl);
		$.ajax({url:surl,success:function(data){
				 if(data.success){
					 var shopCategoryList = data.shopCategoryList;
					 var html = '';
					 html += '<a href="#" class="button" data-category-id="">全部类别</a>';
					 shopCategoryList
					       .map(function(item,index){
					    	  html += '<a href="#" class="button" data-category-id='
					    		  + item.shopCategoryId
					    		  + '>'
					    		  + item.shopCategoryName
					    		  +'</a>';
					    	   
					       });
						$('#shoplist-search-div').html(html);
						
						var selectOptions = '<option value="">全部街道</option>';
						var areaList = data.areaList;
						areaList.map(function(item,index){
					          selectOptions += '<option value="'
					        	            + item.areaId + '">'
					        	            +item.areaName+'</option>';
					          
							
						});
						//将标签机添加进area列表里
						$('#area-search').html(selectOptions);
				 }
				 
			 }
			  
		  });
	
		
		
	}
	//end of getSearchDivData;
	getSearchDivData();
	
	function addItems(pageSize,pageIndex){
		var url = listUrl + '?' + 'pageIndex='+pageIndex+'&pageSize='
		        +pageSize+'&parentId='+parentId+'&areaId='+areaId
		        +'&shopCategoryId='+shopCategoryId+'&shopName='+shopName;
	    loading = true;
	    $.getJSON(url,function(data){
	    	if(data.success){
	    	
	    	maxItems = data.count;
	    	var html='';
	    	
	    	data.shopList.map(function(item,index){
	    		var date = new Date(item.lastEditTime);
	    		var date_str = date.getFullYear() + '-'
	    		               +(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'
	    		               +date.getDate() + ' '
	    		               +date.getHours() + ':'
	    		               +date.getMinutes() + ':'
	    		               +date.getSeconds();
	    		html += ''+'<div class="card" data-shop-id="'
	    		     +item.shopId+'">'+'<div class="card-header">'
	    		     +item.shopName+'</div>'
	    		     +'<div class="card-content">'
	    		     +'<div class="list-block media-list">'+'<ul>'
	    		     +'<li class="item-content">'
	    		     +'<div class="item-media">'+'<img src="'
	    		     +item.shopImg+'" width="44">'+'</div>'
	    		     +'<div class="item-inner">'
	    		     +'<div class="item-subtitle">'+item.shopDesc
	    		     +'</div>'+'</div>'+'</li>'+'</ul>'
	    		     +'</div>'+'</div>'+'<div class="card-footer">'
	    		     +'<p class="color-gray">'
	    		     +date_str
	    		     +'更新</p>'+'<span>点击查看</span>'+'</div>'
	    		     +'</div>'
	    		     
	    		
	    	});
	    	
	    	$('.list-div').append(html);
	    	var total = $('.list-div .card').length;
	    	if(total>=maxItems){
	    	
	    		
	    		$('.infinite-scroll-preloader').hide();
	    	}else{
	    		$('.infinite-scroll-preloader').show();
	    	}
	    	
	    	pageNum += 1;
	    	loading = false;
	    	$.refreshScroller();
			    	}//end Map
	    });
	    
	}
	//end of addItems;
	
	addItems(pageSize, pageNum);
	
	//下滑自动分页
	$(document).on('infinite','infinite-scroll-bottom',function(){
	if(loading)
		return;
	addItems(pageSize,pageNum);
		
		
	});
	
	//点击卡片进入该店详情页
	$('.shop-list').on('click','.card',function(e){
       var shopId = e.currentTarget.dataset.shopId;
       window.location.href = '/o2oCity/frontend/shopdetail?shopId='+shopId;		
	});
	
	
	$('#shoplist-search-div').on(
	'click',
	'.button',
	function(e){
		if(parentId){
			shopCategoryId = e.target.dataset.categoryId;
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				shopCategoryId='';
			}else{
				$(e.target).addClass('button-fill').siblings()
				  .removeClass('button-fill');
			}
			$('.list-div').empty();
			pageNum = 1;
			addItems(pageSize,pageNum);
		}else{
			
			parentId = e.target.dataset.categoryId;
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				parentId='';
			}else{
				$(e.target).addClass('button-fill').siblings()
				.removeClass('button-fill');
			}
			
			$('.list-div').empty();
			pageNum = 1;
			addItems(pageSize,pageNum);
			parentId = '';
		}
		
	});
	$('#search').on('change',function(e){
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize,pageNum);
		
	});

	$('#area-search').on('change',function(){
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum =1 ;
		addItems(pageSize,pageNum);
		
	});
	
	$('#me').click(function(){
		var ss='/o2oCity/wechatlogin/userInfo'
		$.getJSON(ss,function(data){
			var user = data.user;
			$('#u_name').html(user.name);
			$('#u_img').attr("src",user.profileImg);
			
		});
		$.openPanel('#panel-js-demo');
		
	});
	
	$.init();

	
//主函数底部
});
//主函数底部