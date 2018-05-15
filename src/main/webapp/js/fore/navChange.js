$(function () {
  var oWrapper = $('#wrapper');
  var oHeader = $('.header');
  var bg_white = oWrapper.find('.bg-white');
  var nav_item = oWrapper.find('[data-subNav]');
  var sub_nav = oWrapper.find('.sub-nav');
  var subNav_wrapper = sub_nav.find('.sub-nav__wrap');

  var timer = null;

  nav_item.hover(function () {
    clearTimeout(timer);

    hoverChange();

    var getVal = $(this).attr('data-subNav');
    var subNav_cur = $('.sub-nav__wrap[data-link='+getVal+']');
    var cur_imgs = subNav_cur.find('a');

    goodsShow(subNav_cur);
    showImg(cur_imgs);

    nav_theme('hover');
  }, function () {
    timer = setTimeout(function () {
      outChange();

      nav_theme('out');
    }, 100);
  })


  sub_nav.hover(function () {
    clearTimeout(timer);

    nav_theme('hover');
  }, function () {
    timer = setTimeout(function () {
      outChange();

      nav_theme('out');
    }, 100);
  });

  function hoverChange() {
    bg_white.fadeIn('fast');
    sub_nav.animate({height: 139});
  };

  function outChange() {
    bg_white.fadeOut('slow');
    sub_nav.animate({height: 0});
  };

  // 当前商品容器显示
  function goodsShow(subNav_cur) {
    subNav_wrapper.hide();

    subNav_cur.show();
  };

  // 小列表显示
  function showImg(aImg) {
    var num = 0;
    var timer = null;

    aImg.css({opacity: 0, marginLeft: 30});

    for (var i = 0; i < aImg.length; i++) {
      aImg.eq(i).animate({marginLeft: 0, opacity: 1}, 500);

      if (i === aImg.length - 1) {
        clearInterval(timer);
      }
    }


    // 图片移入移出
    aImg.hover(function () {
      $(this).css({opacity: 1}).siblings().css({opacity: 0.7});
    }, function () {
      aImg.css({opacity: 1});
    });
  };


  function nav_theme(method) {
    if (method === 'hover') {
      if (oWrapper.hasClass('theme-white')) {
        oWrapper.addClass('theme-nav');
      }
    } else {
      oWrapper.removeClass('theme-nav');
    }
  };
});