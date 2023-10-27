$(document).ready(function() {

  loadProducts();
  //$('#TablaProd').DataTable();
});

async function loadProducts(){

      const request = await fetch('prod/findAll', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },

      });
      const products = await request.json();

      console.log(products);
      let finalHTML = "";
       for(let product of products.content) {
            let prodHTML='<tr><td>'+product.id+'</td><td>'+product.name+'</td></tr>';
            finalHTML = finalHTML + prodHTML;
       }

        console.log(finalHTML);

      document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
}