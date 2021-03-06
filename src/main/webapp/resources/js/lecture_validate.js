$(document).ready(function() {

	var lecture_id = $("#lecture_id").val();

	var lb_id = $("#lb_id").val();


	$.validator.addMethod("space", function (value, element) {
		return value.indexOf(" ") < 0 && value != "";
	});

	$("#reg_frm").validate({
		onfocusout: false,
		rules: {
			lecture_name: {
				required: true,
				maxlength: 100
			}, prof: {
				required: true,
				maxlength: 20
			}
		}, messages: {
			lecture_name: {
				required: "강의명을 입력하세요.",
				maxlength: $.validator.format("강의명은 100글자 이하로 입력하세요.")
			}, prof: {
				required: "교수명을 입력하세요.",
				maxlength: $.validator.format("교수명은 20글자 이하로 입력하세요.")
			}
		}, submitHandler: function (form) {
			if(confirm("정확하게 입력하셨나요?. 정말 등록하시겠습니까?")){
				$.ajax({
					type: "POST",
					url: "/lecture/reg",
					dataType: "json",
					data: $(form).serialize(),
					success: function (result) {
						if (result) {
							window.open("/lecture/list", "_self");
						} else {
							alert("에러가 발생했습니다. 다시 시도해주세요.");
							window.open("/lecture/list", "_self");
						}

					}
				})
			}
		}
	})

	$("#mod_frm").validate({
		rules: {
			lecture_name: {
				required: true,
				maxlength: 100
			}, prof: {
				required: true,
				maxlength: 20
			}
		}, messages: {
			lecture_name: {
				required: "강의명을 입력하세요.",
				maxlength: $.validator.format("강의명은 100글자 이하로 입력하세요.")
			}, prof: {
				required: "교수명을 입력하세요.",
				maxlength: $.validator.format("교수명은 20글자 이하로 입력하세요.")
			}
		}, submitHandler: function (form) {
			if(confirm("정확하게 입력하셨나요?. 수정하시겠습니까??")){

				$.ajax({
					type: "POST",
					url: "/lecture/mod",
					dataType: "json",
					data: $(form).serialize(),
					success: function (result) {
						if (result) {
							alert("강의 수정이 완료 됐습니다..")
							window.open("/lecture/list", "_self");
						} else {
							alert("이미 삭제된 강의거나 에러가 발생했습니다.");
							window.open("/lecture/list", "_self");
						}
					}
				});

			}
		}
	})

	$("#reg_board_frm").validate({
		onfocusout: false,
		rules: {
			title: {
				required: true,
				maxlength: 40
			},contents: {
				required: true
			},writer: {
				required: true,
				space: true,
				rangelength: [2, 15]
			}
		}, messages: {
			title: {
				required: "제목를 입력하세요.",
				maxlength: $.validator.format("제목은 40글자 이하로 입력하세요.")
			}, contents: {
				required: "내용을 입력하세요."
			}, writer: {
				required: "닉네임을 입력하세요.",
				space: "공백은 불가능합니다.",
				rangelength: $.validator.format("닉네임은 2 글자 이상 15글자 이하로 입력하세요.")
			}
		}, submitHandler: function (form) {
			$.ajax({
				type: "POST",
				url: "/lecture/boardReg",
				dataType: "json",
				data: $(form).serialize(),
				success: function (result) {
					if (result) {
						window.open("/lecture/boardList/"+$("#lecture_id").val()+"/", "_self");
					} else {
						alert("에러가 발생했습니다. 다시 시도해주세요.");
					}
				}
			})
		}
	})

	$("#mod_board_frm").validate({
		onfocusout: false,
		rules: {
			title: {
				required: true,
				maxlength: 40
			},contents: {
				required: true
			}
		}, messages: {
			title: {
				required: "제목를 입력하세요.",
				maxlength: $.validator.format("제목은 40글자 이하로 입력하세요.")
			}, contents: {
				required: "내용을 입력하세요."
			}
		}, submitHandler: function (form) {
			$.ajax({
				type: "POST",
				url: "/lecture/boardMod",
				dataType: "json",
				data: $(form).serialize(),
				success: function (result) {
					if (result) {
						window.open("/lecture/boardView/"+lecture_id+"/?lb_id="+lb_id, "_self");
					} else {
						alert("이미 삭제된 글이거나 에러가 발생했습니다.");
						window.open("/lecture/boardList/"+lecture_id+"/", "_self");
					}
				}
			})
		}
	})

/*	$("#reReg_frm").validate({
		onfocusout: false,
		rules: {
			contents: {
				required: true
			},writer: {
				required: true,
				space: true,
				rangelength: [2, 20]
			}
		}, messages: {
			contents: {
				required: "내용을 입력하세요."
			}, writer: {
				required: "닉네임을 입력하세요.",
				space: "공백은 불가능합니다.",
				rangelength: $.validator.format("닉네임은 2 글자 이상 20글자 이하로 입력하세요.")
			}
		}, submitHandler: function (form) {
			$.ajax({
				type: "POST",
				url: "/lecture/boardReplyReg",
				dataType: "json",
				data: $(form).serialize(),
				success: function (result) {
					if (result) {
						location.reload();
					} else {
						alert("죄송합니다 다시 시도해주세요.")
						location.reload();
					}

				}
			})
		}
	})*/

})