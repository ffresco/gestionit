
    $(document).ready(function(){
      var date_input=$('input[id="txtCotFechaDesde"]'); //our date input has the name "date"
      var container=$('.form-horizontal').length>0 ? $('.form-horizontal').parent() : "body";
      var options={
        dateFormat: 'dd/mm/yy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
      $('#txtCotFechaDesde').datepicker();
      $('[data-toggle="popover"]').popover(); 
      $('[data-toggle="tooltip"]').tooltip(); 
    });


    function cargarContenido(direccion) {
     $.ajax({
         url : direccion,
         success : function(data) {
             $('#result').html(data);
         }
     });
     }

     function upperCaseF(a){
         setTimeout(function(){
             a.value = a.value.toUpperCase();
         }, 1);
     }

  //Recibe El numero de columna de la tabla donde buscar la primera coluna es 0, el segundo parametro
  //es el input de donde sacar el texto a buscar
  function filterTable(columna,txtSearch) {
  // Declare variables 
  var input, filter, table, tr, td, i;
  //input = document.getElementById("txtSearch");
  input = txtSearch;
  filter = input.value.toUpperCase();
  table = document.getElementById("searchTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[columna];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    } 
  }
}


  




