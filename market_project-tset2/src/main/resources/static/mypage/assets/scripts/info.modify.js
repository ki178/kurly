
//region modify 페이지 회원 정보 수정
const $modifyForm = document.getElementById('modify-form');

$modifyForm.onsubmit = (e) => {
    e.preventDefault();

    if($modifyForm['password'].value.length < 10 || $modifyForm['password'].value.length > 16 || $modifyForm['password'].value.length < 10){
        Dialog.show({
            content: '비밀번호 길이 확인',
            buttons: [{
                text: '확인', onclick: ($dialog) => Dialog.hide($dialog)
            }]
        });
        return;
    }
    if($modifyForm['password'].value !== $modifyForm['password-check'].value){
        Dialog.show({
            content: '비밀번호 불일치',
            buttons: [{
                text: '확인', onclick: ($dialog) => Dialog.hide($dialog)
            }]
        });
        return;
    }
    if($modifyForm['userName'].value.length < 1 || $modifyForm['userName'].value.length > 15){
        Dialog.show({
            content: '이름을 확인해 주세요.',
            buttons: [{
                text: '확인', onclick: ($dialog) => Dialog.hide($dialog)
            }]
        });
        return;
    }

    const formData = new FormData();
    formData.append('id', $modifyForm['userId'].value);
    formData.append('password', $modifyForm['password'].value);
    formData.append('userName', $modifyForm['userName'].value);
    formData.append('email', $modifyForm['email'].value);
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE){
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            Dialog.show({
                content: `요청을 전송하는 도중 오류가 발생하였습니다.<br>잠시 후 다시 시도해 주세요.`,
                buttons: [{
                    text: '확인',
                    onclick: ($dialog) => Dialog.hide($dialog)
                }]
            });
            return;
        }
        const response = JSON.parse(xhr.responseText);
        if(response['result'] === 'success'){
            Dialog.show({
                content: `회원정보가 수정되었습니다.<br>다시 로그인 해주세요.`,
                buttons: [{
                    text: '확인',
                    onclick: ($dialog) => {
                        Dialog.hide($dialog);
                        location.reload();
                    }
                }]
            });
        } else {
            Dialog.show({
                content: '수정에 실패하였습니다. 잠시 후 다시 시도해 주세요.',
                buttons: [{
                    text: '확인',
                    onclick: ($dialog) => {
                        Dialog.hide($dialog);
                        location.reload();
                    }
                }]
            });
        }
    };
    xhr.open('PATCH', '/mypage/info/modify');
    xhr.send(formData);
};
//endregion




