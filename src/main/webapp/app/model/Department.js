Ext.define('ExtjsTrial.model.Department', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'id', type: 'int' },
        { name: 'name', type: 'string' },
        { name: 'lastModified', type: 'string' }

    ]
});
