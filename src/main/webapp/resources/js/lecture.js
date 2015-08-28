var lecture_id = $("#lecture_id").val();

var lb_id = $("#lb_id").val();

function searchLecture() {
	if(document.search_frm.searchWord.value.length == 0) {
		alert("검색어를 입력해 주세요.");
		search_frm.searchWord.focus();
		return;
	}

	document.search_frm.submit();
}

function likeAjax(lecture_id) {

	$.ajax({
		type     : "POST",
		url      : "/lecture/deleteConfirm",
		dataType : "json",
		data     : {lecture_id:lecture_id},
		success  : function(result) {
			if(result){
				alert("이미 삭제 된 강의입니다.");
				location.reload();
			} else {
				$.ajax({
					type     : "POST",
					url      : "/lecture/likes",
					dataType : "json",
					data     : {lecture_id:lecture_id},
					success  : function(result2) {

						if(result2 === 1) {
							alert("추천 되었습니다");
							location.reload();
						}else if(result2 === 2){
							alert("이미 추천하였습니다.");
						}else {
							alert("에러가 발생했습니다. 다시 시도해주세요.");
							location.reload();
						}
					}
				});
			}
		}
	});
}

function modAjax(lecture_id) {
	$.ajax({
		type: "POST",
		url: "/lecture/confirm",
		dataType: "json",
		data: {lecture_id: lecture_id},
		success: function (result) {

			if (result) {
				window.open('/lecture/view?lecture_id='+lecture_id, '_self');
			} else {
				alert("본인이 작성하신 글이 아닙니다.");
			}
		}
	})
}

function deleteAjax(lecture_id) {

	$.ajax({
		type: "POST",
		url: "/lecture/deleteConfirm",
		dataType: "json",
		data: {lecture_id: lecture_id},
		success: function (result) {
			if(result){
				alert("이미 삭제 된 강의입니다.");
				location.reload();
			} else {
				$.ajax({
					type: "POST",
					url: "/lecture/confirm",
					dataType: "json",
					data: {lecture_id: lecture_id},
					success: function (result2) {
						if (result2) {
							if (confirm("정말 삭제하시겠습니까?")) {
								$.ajax({
									type: "POST",
									url: "/lecture/isDelete",
									dataType: "json",
									data: {lecture_id: lecture_id},
									success: function (result3) {
										if (result3) {
											alert("삭제되었습니다.");
											location.reload();
										} else {
											alert("에러가 발생했습니다. 다시 시도해주세요.");
											location.reload();
										}
									}
								})
							}
						} else {
							alert("본인이 작성하신 글이 아닙니다.");
						}
					}
				})
			}
		}
	})
}

$(document).ready(function() {

	var bd = $("body");

	bd.on("dblclick", function () {

		window.scrollTo(0, 0);

	})

})
