Ext.define("ExtjsTrial.view.department.DepartmentAdd", {
    extend: 'Ext.form.Panel',
    requires: [
        'Ext.form.Panel'
    ],
    alias: 'widget.departmentadd',
    
    title: 'Add New Department',
    layout: 'fit',
    autoShow: true,
    
    initComponent: function(){
    	this.items = [
    	    {
				xtype: 'form',
				items: [
				    {
						xtype: 'textfield',
						name : 'name',
						fieldLabel: 'Name'
				    }
				],
				buttons: [
				    {
				        text: 'Add',
				    	action: 'save'
				    }
				]
    	    }
    	];
//    	this.buttons = [
//    	    {
//    	    	text: 'Add',
//    	    	action: 'save'
//    	    }
//    	];
    	this.callParent(arguments);
    }
    
});