// Call the dataTables jQuery plugin
$(document).ready(function() {

  loadUser();
  $('#TablaUser').DataTable();
});

async function loadUser(){

      const request = await fetch('userList', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },

      });
      const usuarios = await request.json();

      console.log(usuarios);
      let finalHTML = "";
       for(let usuario of usuarios) {
            let userHTML='<tr><td>'+usuario.nombre+'</td><td>'+usuario.id+'</td></tr>';
            finalHTML = finalHTML + userHTML;
       }

        console.log(finalHTML);

      document.querySelector('#TablaUser tbody').outerHTML=finalHTML;
}