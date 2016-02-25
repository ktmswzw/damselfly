/**
 * Created by Vincent on 2015/1/21.
 */

requirejs(['jquery'],
    function () {
        requirejs(['bootstrap', 'ie10', 'comm'],
            function () {
                if($("#"+current)!=undefined)
                {
                    $("#"+current).addClass("active");
                }
            });
    });