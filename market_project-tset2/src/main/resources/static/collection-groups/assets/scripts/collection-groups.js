const $items = document.querySelectorAll('#main > .top > .menu > .item');

$items.forEach((x) => x.addEventListener('mouseenter', () => {
    x.classList.add('hover');
    x.addEventListener('mouseleave', () => {
        $items.forEach((x) => x.classList.remove('hover'));
    });
}));

const $filterBox = document.querySelectorAll('.container > .content-box > .filter > .filter-area > .filter-box');

$filterBox.forEach((x) => {
    const $button = x.querySelector(':scope > .filter-button');
    const $nav = x.querySelector(':scope > .nav');
    $nav.style.opacity = '1';

    $button.onclick = () => {
        if($nav.style.opacity === '1'){
            $button.querySelector(':scope > .svg').style.transform = 'rotate(180deg)';
            $nav.style.opacity = '0';
            $nav.style.maxHeight = '0';
        } else {
            $button.querySelector(':scope > .svg').style.transform = 'rotate(0deg)';
            $nav.style.opacity = '1';
            $nav.style.maxHeight = '100vh';
        }
    };
});


const $menuList = Array.from(document.querySelectorAll('#nav > .header-wrapper > ul > .item1 > a'));
const $title = document.querySelector('.top > .title');
$menuList.forEach((x) => {
    if($title.innerText === x.innerText){
        x.classList.add('active');
    }
});

const $cartInButton = document.querySelectorAll('#main > .container > .content-box > .item-box > .item-container > .item > .button-wrapper > .button');

$cartInButton.forEach((x) => x.onclick = () => {
    alert('장바구니 담기 구현해야됨');
});