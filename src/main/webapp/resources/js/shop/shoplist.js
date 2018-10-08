/**
 * 
 */
$(function(){
	getshoplist();
	function getshoplist(){
		$.ajax({
			url:"/o2oCity/shopadmin/getshoplist",
			type:"GET",
			dataType:"JSON",
			success:function(data){
				var obj = JSON.parse(data);

				if(obj.success){
					
					handleList(obj.shopList);
					handleUser(obj.user);
					
				}
			}
				
		})
	}
	function handleUser(data) {
		$('#user-name').text(data.name);
	}
	function handleList(data){
		var html = '';
		data.map(function(item,index){
			html += '<div class="row row-shop"><div class="col-40">'
				+item.shopName+'</div><div class="col-40">'
				+shopStatus(item.enableStatus)
				+'</div><div class="col-20>">'
				+goShop(item.enableStatus,item.shopId)+'</div></div>';
		});
		$('.shop-wrap').html(html);
	}
	
	function shopStatus(status){
		if(status==0){
			return '审核中';
		}else if(status==-1){
			return '店铺非法';
		}else if(status ==1){
			return '审核通过';
		}
	}
	
	function goShop(status,id){
		if(status == 1){
			return '<a href="/o2oCity/shop/shopmanagement?shopId='+id+'" external>进入</a>';
		}else{
			return'';
		}
	}
	
})