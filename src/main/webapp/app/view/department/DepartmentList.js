Ext.define("ExtjsTrial.view.department.DepartmentList", {
    extend: 'Ext.grid.Panel',
    alias: 'widget.departmentlist',
    
    title: 'Departments',
    
    store: 'DepartmentStore',
    
    initComponent: function(){
    	this.columns = [
    	    {header: 'Id', dataIndex: 'id',  flex: 1},
    	    {header: 'Name', dataIndex: 'name',  flex: 1},
    	    {header: 'Last Modified', dataIndex: 'lastModified',  flex: 1}
    	];
    	this.callParent(arguments);
    }
    
});