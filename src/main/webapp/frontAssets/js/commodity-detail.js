/**
 * Created by dushang on 2016/9/12.
 */
$(function () {
    var unslider=$('.my-slider').unslider({
        speed: 1000,               //  The speed to animate each slide (in milliseconds)
        delay: 3000,              //  The delay between slide animations (in milliseconds)
        fluid: false,             //  Support responsive design. May break non-responsive designs
        autoplay:true,
        arrows:false,
        infinite:true
    });
    /*手机端触屏*/
    $('.my-slider').on('swipeleft', function(e) {
        unslider.stop().prev();
    }).on('swiperight', function(e) {
        unslider.stop().next();
    });


    $('.timer').each(function (i,value) {
        var endTime=$(this).attr('data-endTime');
        $(this).downCount({
            date:endTime,
            offset:+10
        },function () {
            $('.timer').eq(i).find('li span').css({
                "color":"#FF463C",
                "text-shadow":"0 1px 0 #FF463C"
            });
            $('#buyBtn').css({
                "background-color":"#ccc",
                "cursor":'not-allowed'
            }).click(function (e) {
                e.preventDefault();
            });
        })
    });

    if($('.prompt-info').text()){
        setTimeout($('.prompt-info').fadeOut(8000),10000);
    }

});
