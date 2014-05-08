Ext.define("ExtjsTrial.view.department.DepartmentEdit", {
    extend: 'Ext.window.Window',
    requires: [
        'Ext.form.Panel'
    ],
    alias: 'widget.departmentedit',
    
    title: 'Edit Department',
    layout: 'fit',
    autoShow: true,
    
    initComponent: function(){
    	this.items = [
    	    {
    	    	xtype: 'form',
				items: [
				    {
					xtype: 'displayfield',
					name : 'id',
					fieldLabel: 'Id'
				    },
				    {
					xtype: 'textfield',
					name : 'name',
					fieldLabel: 'Name'
				    },
				    {
					xtype: 'displayfield',
					name : 'lastModified',
					fieldLabel: 'Last Modified'
				    }
				]
    	    }
    	];
    	this.buttons = [
    	    {
    	    	text: 'Save',
    	    	action: 'save'
    	    },
    	    {
    	    	text: 'Cancel',
    	    	scope: this,
    	    	handler: this.close
    	    }
    	];
    	this.callParent(arguments);
    }
    
});