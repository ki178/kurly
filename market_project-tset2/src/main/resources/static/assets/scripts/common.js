
window.addEventListener("scroll", (e) => {
    const $nav = document.getElementById('nav');
    const $notice = $nav.querySelector(':scope > .header-wrapper > div > .notice');
    let scrollY = this.scrollY;
    if(scrollY < 100){
        $nav.style.position = 'relative';
        $notice.style.display = 'block';
        $nav.querySelector(':scope > .header-wrapper').classList.remove('fixed');
        $nav.querySelector(':scope > .header-wrapper > ul').classList.remove('fixed');
        $nav.querySelector(':scope > .header-wrapper > .icon-container').classList.remove('fixed');
        $nav.querySelector(':scope > .header-wrapper > .fixed-search').style.display = 'none';
    } else {
        $nav.style.position = 'fixed';
        $notice.style.display = 'none';
        $nav.querySelector(':scope > .header-wrapper').classList.add('fixed');
        $nav.querySelector(':scope > .header-wrapper > ul').classList.add('fixed');
        $nav.querySelector(':scope > .header-wrapper > .icon-container').classList.add('fixed');
        $nav.querySelector(':scope > .header-wrapper > .fixed-search').style.display = 'flex';
    }
});


HTMLElement.prototype.hide = function () {
    this.classList.remove('-visible');
    return this;
}

HTMLElement.prototype.show = function () {
    this.classList.add('-visible');
    return this;
}

class Dialog {
    /** @type {HTMLElement} */
    static $cover;
    /** @type {Array<HTMLElement>} */
    static $dialogArray= [];

    /**
     * @param {string} content
     * @param {function(HTMLElement)|undefined} onclick
     * */
    static defaultOk(content, onclick = undefined){
        Dialog.show({
            content: content,
            buttons: [{
                text: '확인', onclick: ($dialog) => {
                    Dialog.hide($dialog);
                    if(typeof onclick === 'function'){
                        onclick($dialog);
                    }
                }
            }]
        });
    }


    /**
     * @param {HTMLElement} $dialog
     */
    static hide($dialog) {
        Dialog.$dialogArray.splice(Dialog.$dialogArray.indexOf($dialog), 1);
        if(Dialog.$dialogArray.length === 0){
            Dialog.$cover.hide();
        }
        $dialog.hide();
        setTimeout(() => $dialog.remove(), 1000);
    }

    /**
     * @param {Object} args
     * @param {string} args.content
     * @param {Array<{text: string, onclick: function}>|undefined} args.buttons
     * @param {number} delay
     * @returns {HTMLElement}
     */
    static show(args, delay = 50) {
        const $dialog = document.createElement('div');
        $dialog.classList.add('---dialog');
        const  $content = document.createElement('div');
        $content.classList.add('_content');
        $content.innerHTML = args.content;
        $dialog.append($content);
        if(args.buttons != null && args.buttons.length > 0){
            const $buttonContainer = document.createElement(('div'));
            $buttonContainer.classList.add('_button-container');
            $buttonContainer.style.gridTemplateColumns = `repeat(${args.buttons.length}, 1fr)`;
            for(const button of args.buttons) {
                const $button = document.createElement('button');
                $button.classList.add('_button');
                $button.setAttribute('type', 'button');
                $button.innerText = button.text;
                if(typeof button.onclick === 'function'){
                    $button.onclick = () => button.onclick($dialog);
                }
                $buttonContainer.append($button);
            }
            $dialog.append($buttonContainer);
        }
        document.body.prepend($dialog);
        Dialog.$dialogArray.push($dialog);
        if(Dialog.$cover == null){
            const $cover = document.createElement('div');
            $cover.classList.add('---dialog-cover');
            document.body.prepend($cover);
            Dialog.$cover = $cover;
        }
        setTimeout(() => {
            $dialog.show();
            Dialog.$cover.show();
        }, delay);
        return $dialog;
    }
}

class Dialog2 {
    static showDialog({
                          message = "삭제하시겠습니까?",
                          onConfirm = () => {},
                          onCancel = () => {}
                      }) {
        // 다이얼로그 오버레이 생성
        const dialogCover = document.createElement('div');
        dialogCover.className = '---dialog-cover2'; // 흐릿한 배경 클래스

        // 다이얼로그 컨테이너 생성
        const dialog = document.createElement('div');
        dialog.className = '--dialog'; // 다이얼로그 컨테이너 클래스

        // 다이얼로그 제목 및 버튼 컨테이너 생성
        const title = document.createElement('div');
        title.className = '_title';

        const question = document.createElement('p');
        question.className = '_question';
        question.textContent = message;

        // 버튼 컨테이너를 제목 안으로 이동
        const buttonContainer = document.createElement('div');
        buttonContainer.className = '_button-container';

        // 취소 버튼 생성
        const cancelButton = document.createElement('button');
        cancelButton.className = 'cancel _button';
        cancelButton.setAttribute('aria-label', 'cancel-button');
        cancelButton.textContent = '취소';
        cancelButton.onclick = () => {
            document.body.removeChild(dialogCover); // 다이얼로그 제거
            if (typeof onCancel === 'function') {
                onCancel();
            }
        };

        // 확인 버튼 생성
        const confirmButton = document.createElement('button');
        confirmButton.className = 'confirm _button';
        confirmButton.setAttribute('aria-label', 'confirm-button');
        confirmButton.textContent = '확인';
        confirmButton.onclick = () => {
            document.body.removeChild(dialogCover); // 다이얼로그 제거
            if (typeof onConfirm === 'function') {
                onConfirm();
            }
        };

        // 버튼 컨테이너에 버튼 추가
        buttonContainer.appendChild(cancelButton);
        buttonContainer.appendChild(confirmButton);

        // 제목 안에 질문과 버튼 컨테이너 추가
        title.appendChild(question);
        title.appendChild(buttonContainer);

        // 다이얼로그에 제목 추가
        dialog.appendChild(title);

        // 오버레이에 다이얼로그 추가
        dialogCover.appendChild(dialog);

        // 문서에 추가
        document.body.appendChild(dialogCover);
    }
}


const $category = document.querySelector('#nav > .header-wrapper > .category');
const $categoryMenu = $category.querySelector(':scope > .category-container > .category-menu');
const $menu = $category.querySelector(':scope > .category-container > .category-menu > .menu');
const $submenu = $menu.querySelector(':scope > .item1 > .submenu');

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = (string, type) => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert('오류');
            return;
        }
        const response = JSON.parse(xhr.responseText);
        $menu.innerHTML = '';
        for (const category of response['categories']) {
            const $item = new DOMParser().parseFromString(`
                    <ul class="menu">
                            <li class="item1">
                                <div class="img-cover">
                                    <img src="${category['categoryImg']}" alt="${category['categoryName']}" class="image">
                                    <span class="category-name">${category['categoryName']}</span>
                                    <label>
                                        <input type="hidden" class="categoryId" name="${category['categoryId']}" value="${category['categoryId']}">
                                    </label>
                                </div>
                            </li>
                        </ul>
                    `, 'text/html').querySelector('.item1');

            $menu.append($item);
        }

    };
    xhr.open('GET', '/item/categories');
    xhr.send();



setTimeout(() => {
    const $items = Array.from(document.querySelectorAll('.category-container > .category-menu > .menu > .item1'));
    $items.forEach((x) => x.addEventListener('mouseleave', () => x.classList.remove('hover')));
    $items.forEach((x) => x.addEventListener('mouseenter', () => {
        x.classList.add('hover');

        const categoryId = x.querySelector(':scope > .img-cover > label > .categoryId').value;
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if (xhr.readyState !== XMLHttpRequest.DONE){
                return;
            }
            if (xhr.status < 200 || xhr.status >= 300) {
                alert('오류');
                return;
            }
            const response = JSON.parse(xhr.responseText);
            $submenu.innerHTML = '';
            for(const subCategory of response['subCategories']){
                const $subitem = new DOMParser().parseFromString(`
                    <ul class="submenu">
                        <li class="subitem">
                            <div class="text-box">
                                 <span class="text">${subCategory['subCategoryName']}</span>
                                 <label>
                                    <input class="subCategoryId" type="hidden" name="subCategoryId" value="${subCategory['subCategoryId']}">
                                 </label>
                            </div>
                        </li>
                    </ul>
                    `, 'text/html').querySelector('.subitem');
                $submenu.append($subitem);
            }
            x.append($submenu);

        };
        xhr.open('GET', `/item/sub-categories?categoryId=${categoryId}`);
        xhr.send();

    }));
}, 100);


const $pickButton = document.getElementById('pick-button');
const $cartButton = document.getElementById('cart-button');

$pickButton.onclick = () => {
    const url = new URL(location.href);
    url.pathname = '/mypage/pick';
    location.href = url.toString();
}














