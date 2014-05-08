Ext.define('ExtjsTrial.store.DepartmentStore', {
    extend: 'Ext.data.Store',
    requires: [
    	'Ext.data.proxy.Rest'
    ],
    model: 'ExtjsTrial.model.Department',
    autoLoad: true,
    //autoSync: true,
    proxy: {
		type: 'rest',
		api: {
		    read: '/cache-optlocking/rest/department/all',
		    update: '/cache-optlocking/rest/department/update',
            write: '/cache-optlocking/rest/department/insert',
            destroy: '/cache-optlocking/rest/department/delete'
		},
		reader: {
		    type: 'json'
		},
		writer: {
            type: 'json'
        }
    }
    
});
