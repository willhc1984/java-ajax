$(document).ready(function(){
	
	moment.locale('pt-br');
	
	var table = $("#table-server").DataTable({
		
		processing: true,
		serverSide: true,
		responsive: true,
		lenghtMenu: [ 10, 15, 20, 25],
		ajax: {
			url: "/promocao/datatables/server",
			data: "data"
		},
		columns: [
			{data: 'id'},
			{data: 'titulo'},
			{data: 'site'},
			{data: 'linkPromocao'},
			{data: 'descricao'},
			{data: 'linkImagem'},
			{data: 'preco', render: $.fn.dataTable.render.number('.', ',', 2, 'R$')},
			{data: 'likes'},
			{data: 'dataCadastro', render: 
				function(dataCadastro){
					return moment(dataCadastro).format('LLL');
				}
			},
			{data: 'categoria.titulo'}
		],
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'Editar',
				attr: {
					id: 'btn-editar',
					type: 'button'
				},
				enabled: false
			},
				
			{
				text: 'Excluir',
				attr: {
					id: 'btn-excluir',
					type: 'button'
				},
				enabled: false
			}
		]
	});
	
	//ação para marcar/desmarcar botoes ao clicar nas ordenações
	$("#table-server thead").on('click', 'tr', function(){
			table.buttons().disable();		
	});
	
	//ação para marcar/desmarcar linhas clicadas
	$("#table-server tbody").on('click', 'tr', function(){
		
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');
			table.buttons().disable();
		}else{
			$('tr.selected').removeClass('selected');
			$(this).addClass('selected');	
			table.buttons().enable();		
		}
	});
	
	//ação do botão de editar
	$("#btn-editar").on('click', function(){
				
		if(isSelectedRow()){
			$("#modal-form").modal('show');
			var id = getPromoId();
		}
		
	});
	
	//ação do botão de excluir
	$("#btn-excluir").on('click', function(){
		if(isSelectedRow()){
			$("#modal-delete").modal('show');
			var id = getPromoId();
		}
	});
	
	function getPromoId(){
		return table.row(table.$('tr.selected')).data().id;
	}
	
	function isSelectedRow(){
		var trow =  table.row(table.$('tr.selected'));
		return trow.data() !== undefined;
	}
	
});