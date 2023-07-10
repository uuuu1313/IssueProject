const commonLib = {

};

window.addEventListener("DOMContentLoaded", function() {
    /* 주소 찾기 팝업 처리 */
    const searchAddressEls = document.getElementsByClassName("search_address")
    for(const el of searchAddressEls) {
        el.addEventListener("click", function() {
            const dataset = this.dataset;
            new daum.Postcode({
                    oncomplete: function(data) {
                        if (typeof searchAddressCallback == 'function') {
                            searchAddressCallback(data);
                            return;
                        }

                        const addressId = dataset.addressId;
                        const zipcodeId = dataset.zipcodeId;

                        if (!addressId || !zipcodeId) {
                            return;
                        }

                        const zipcodeEl = document.getElementById(zipcodeId);
                        const addressEl = document.getElementById(addressId);
                        if (zipcodeEl) zipcodeEl.value = data.zonecode;
                        if (addressEl) addressEl.value = data.roadAddress;
                    }
            }).open();
        });
    }
    /* 주소 찾기 팝업 완료 */

    /* 마이페이지 사이드바 S */
    const menu = document.getElementById("jsMenu");

    function slideDown() {
        menu.style.left="-350px";
    }

    function slideUp() {
        menu.style.left="-100px";
    }

    function handleMouseMove(event) {
        let clientX = event.clientX;

        if (clientX >= 0 && clientX <= 10) {
            slideUp();
        } else if (clientX > 300) {
            slideDown();
        }
    }

    function init() {
        document.addEventListener("mousemove", handleMouseMove);
    }
    init();
    /* 마이페이지 사이드바 E */
});