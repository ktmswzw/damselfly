function meForm(target, data){

    _load(data);

    function _load(data){
        var form = $(target);
        for(var name in data){
            var val = data[name];
            var count = _loadOther(name, val);
            if (!count){
                $('input[name="'+name+'"]', form).val(val);
                $('textarea[name="'+name+'"]', form).val(val);
                $('select[name="'+name+'"]', form).val(val);
            }
        }
    }


    function _loadOther(name, val){
        var count = 0;
        var pp = ['textbox','numberbox','slider'];
        for(var i=0; i<pp.length; i++){
            var p = pp[i];
            var f = $(target).find('input['+p+'Name="'+name+'"]');
            if (f.length){
                f[p]('setValue', val);
                count += f.length;
            }
        }
        return count;
    }

}