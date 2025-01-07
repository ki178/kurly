new Swiper('.swiper', {
    slidesPerView: 4,
    slidesPerGroup: 4,
    speed: 500,


    navigation: {
        prevEl: '.prev',
        nextEl: '.next',
    },
});

new Swiper('.swiper2', {
    slidesPerView: 5,
    slidesPerGroup: 5,
    speed: 500,
    spaceBetween: 23.5,

    navigation: {
        prevEl: '.prev2',
        nextEl: '.next2',
    },
});

new Swiper('.swiper3', {
    slidesPerView: 5,
    speed: 500,
    spaceBetween: 23.5,
    loop: true,

    autoplay: {
        delay: 900,
        disableOnInteraction: false,
    },

    navigation: {
        prevEl: '.prev3',
        nextEl: '.next3',
    },
});

const $cartBtn = document.querySelectorAll('.slide-container > .swiper-initialized > .swiper-wrapper > .swiper-slide > .link > div > button');

$cartBtn.forEach((x) => x.onclick = () => {

});

