Ext.define('ExtjsTrial.controller.DepartmentController', {
    extend: 'Ext.app.Controller',
    
    models:[
    	'Department'
    ],
    
    views:[
    	'department.DepartmentList',
    	'department.DepartmentEdit',
    	'department.DepartmentAdd'
    ],
    
    stores: [
    	'DepartmentStore'
    ],
    
    init: function() {
        this.control(
        {
            'departmentlist':{
            	render: this.onListRender,
            	itemdblclick: this.editDepartment
            },
            'departmentadd':{
            	render: this.onAddRender
            },
            'departmentadd button[action=save]':{
            	click: this.addDepartment
            },
            'departmentedit button[action=save]':{
            	click: this.updateDepartment
            }
        });
    },
    
    onListRender: function(){
    	console.log('departmentlist rendered');
    },
    
    onAddRender: function(){
    	console.log('departmentadd rendered');
    },
    
    editDepartment: function(grid, record){
    	console.log('double clicked on '+record.get('name'));
    	var view = Ext.widget('departmentedit');
    	view.down('form').loadRecord(record);
    },
    
    updateDepartment: function(button){
    	console.log('updating department');
    	var win = button.up('window'),
    	    form = win.down('form'),
    	    record = form.getRecord(),
    	    vals = form.getValues();
    	record.set(vals);
    	win.close();
    	this.getDepartmentStoreStore().sync();
    },
    
    addDepartment: function(button){
    	console.log('adding department');
    	var panel = button.up('panel'),
    		form = panel.down('form'),
    		record = form.getRecord(),
    		vals = form.getValues();
    	record.set(vals);
    	this.getDepartmentStoreStore().sync();
    }
    
});
