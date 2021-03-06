(function($) {

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader("AJAX", true);
        },
        error: function(xhr, status, err) {
            if (xhr.status == 401) {
                alert("인증에 실패 했습니다. 로그인 페이지로 이동합니다.");
            } else if (xhr.status == 403) {
                if (confirm("회원만 가능합니다. 로그인페이지로 이동하겠습니다.")) {
                    location.href = "/login";
                }

            } else {
                alert("예외가 발생했습니다. 관리자에게 문의하세요.");
            }
        }
    });

})(jQuery);
