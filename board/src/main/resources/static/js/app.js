const main ={
	init : function(){
		const _this = this;
		
		// 게시물 저장
		$('#btn-save').on('click', function() {
			_this.save();
		});
		
		// 게시물 수정
		$('#btn-update').on('click', function(){
			_this.update();
		});
		
		// 게시글 삭제
		$('#btn-delete').on('click', function(){
			_this.delete();
		});
		
		/* 댓글 부분 */
		
		// 댓글 저장
		$('#btn-comment-save').on('click', function(){
			_this.commentSave();
		});
		
		// 댓글 수정
		document.querySelectorAll('#btn-comment-update').forEach(function(item){
			item.addEventListener('click', function(){ // 버튼 클릭 이벤트 발생 시
				const form = this.closet('form'); // btn의 가장 가까운 조상의 Element(form) 반환 (closest)
				_this.commentUpdate(form); // 해당 폼으로 업데이트 수행
			})
		})
		
	},
	
	/* 글 작성 */
	save : function(){
		const data = {
			title: $('#title').val(),
			content: $('#content').val()
		};
		
		// 공백 및 빈 문자열 체크
		if(!data.title || data.title.trim() === "" || !data.content || data.content.trim() ===""){
			alert("공백 또는 입력하지 않은 부분이 있습니다.");
			return false;
		} else{
			const con_check = confirm("등록하시겠습니까?");
			if(con_check == true){
				$.ajax({
					type: 'POST',
					url: '/api/post',
					dataType: 'JSON',
					contentType: 'application/json; charset=utf-8',
					data: JSON.stringify(data)
				}).done(function() {
					alert('등록되었습니다.');
					window.location.href='/list';
				}).fail(function (error){
					alert(JSON.stringfy(error));
				});
			}
		}
	},
	
	/* 게시글 수정 */
	update : function(){
		const data = {
			id: $('#id').val(),
			title: $('#title').val(),
			content: $('#content').val()
		};
		
		const con_check = confirm("수정하시겠습니까?");
		if(con_check === true){
			if(!data.title || data.title.trim() === "" || !data.content || data.content.trim() === ""){
				alert("공백 또는 입력하지 않은 부분이 있습니다.");
				return false;
			} else{
				$.ajax({
					type: 'PUT',
					url: '/api/post/'+data.id,
					dataType: 'JSON',
					contentType: 'application/json; charset=utf-8',
					data: JSON.stringify(data)
				}).done(function(){
					alert("수정이 완료되었습니다.");
					window.location.href = '/post/read/'+data.id;
				}).fail(function(error){
					alert(JSON.stringify(error));
				});
			}
		}
	},
	
	/* 게시글 삭제 */
	delete : function() {
		const id = $('#id').val();
		const con_check = confirm("정말 삭제하시겠습니까?");
		
		if(con_check == true){
			$.ajax({
				type : 'DELETE',
				url : '/api/post/'+id,
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8'
			}).done(function(){
				alert("삭제되었습니다.");
				window.location.href="/list";
			}).fail(function (error) {
				alert(JSON.stringfy(error));
			});
		} else{
			return false;
		}
	},
	
	
	/* 댓글 부분 */
	
	// 댓글 저장
	commentSave : function() {
		const data = {
			postId: $('#postId').val(),
			content: $('#comment').val()
		};
		
		// 공백 및 빈 문자열 체크
		if(!data.content || data.content.trim() === ""){
			alert('공백 또는 입력하지 않은 부분이 있습니다.');
			return false;
		} else {
			$.ajax({
				type: 'POST',
				url: '/api/post/'+data.postId+'/comment',
				datatype: 'JSON',
				contentType: 'application/json; charset=utf-8',
				data: JSON.stringify(data)
			}).done(function(){
				alert('댓글이 등록되었습니다');
				window.location.reload();
			}).fail(function(){
				alert(JSON.stringfy(error));
			})
		}
	},
	
	// 댓글 수정
	commentUpdate : function(form){
		const data ={
			id : form.querySelector('#id').value,
			postId : form.querySelector('#postId').value,
			comment : form.querySelector('#comment-content').value
		}
		if(!data.comment || data.comment.trim() === ""){
			alert("공백 또는 입력하지 않은 부분이 있습니다.");
			return false;
		}
		const con_check = confirm("수정하시겠습니까?");
		if(con_check === true){
			$.ajax({
				type: 'PUT',
				url: '/api/post/'+data.postId+'/comment/'+data.id,
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8',
				data: JSON.stringify(data)
			}).done(function() {
				window.location.reload();
			}).fail(function(error) {
				alert(JSON.stringify(error));
			})
		}
	},
	
	// 댓글 삭제
	commentDelete : function(postId, commentId){
		con_check = confirm("삭제하시겠습니까?");
		
		if(con_check === true){
			$.ajax({
				type: 'DELETE',
				url: '/api/post/'+postId+'/comment/'+commentId,
				dataType: 'JSON',
			}).done(function(){
				alert('댓글이 삭제되었습니다.');
				window.location.reload();
			}).fail(function(error){
				alert(JSON.stringfy(error));
			});
		}
	}
};

main.init();