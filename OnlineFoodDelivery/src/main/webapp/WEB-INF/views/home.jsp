<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HomePage</title>
        <%@ include file="header.jsp" %>
    </head>
    <script>
	
	Ext.define('ProductModel', {
		extend : 'Ext.data.Model',
		fields:['name','price','resturentName']
	}); 

	Ext.onReady(function() {
	
	
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'ProductModel',
			 autoLoad: true,
			   pageSize: 10,
			   remoteSort: true,
			   sorters: [{
				        sortProperty: 'price',
			              property : 'price',
			              direction: 'asc'
			          }],
			proxy: {
		        type: 'ajax',
		        url : '/fooddelivery/api/customer/product',
		        reader : {
					type : 'json',
					root:'data',
					totalProperty: 'TotalCount'
				}
		    }
		});
		gridStore.load();
		Ext.create('Ext.grid.Panel', {
			id : 'food_id',
			store : gridStore,
			stripeRows : true,
			title : 'Food', 
			renderTo : 'productGrid', 
			collapsible : false, 
			enableColumnMove : true, 
			enableColumnResize : true,
			selType : 'rowmodel',
			dockedItems: [
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    displayInfo: true,
                    store: gridStore
                }
            ],
			columns : [
					{
						header : "Food Name",
						dataIndex : 'name',
						id : 'name',
						flex : 1,	
						sortable : true, 
						hideable : true,
						cls:'F_name',
						editor: new Ext.form.TextField({
						    fieldStyle: 'text-transform:uppercase;',
						    listeners: {
						       change: function(field, newValue, oldValue) {
						           field.setValue(newValue.toUpperCase());
						       }
						    }
						    
						})
					
					},
					{
						header : "Price(Rs.)",
						dataIndex : 'price',
						id : 'price',
						flex : .5,
						sortable : true,
						hideable : false,
						decimalPrecision: 2,
				
					},
					{
						header : "Resurent Name",
						dataIndex : 'resturentName',
						id : 'r_name',
						cls:'r_name',
						flex : .5,
						sortable : true,
						hideable : false
					
					}

					 ]
		});
	});
</script>
    
    <body  style="background-color: powderblue;">


	<marquee direction="right" speed="normal" behavior="loop" >
   <span style=" font: italic bold 12px/30px Georgia, serif;color:red">  Order Food ,Add food and Only COD Available! Have a nice day!</span>
  </marquee>
	<div id="productGrid" style="margin-top: 27px">
	
	</div>
        <%@ include file="footer.jsp" %>
    </body>
</html>
