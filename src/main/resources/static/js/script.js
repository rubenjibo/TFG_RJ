let products = [];
let currentPage = 0;
const itemsPerPage = 10;

$(document).ready(async function() {

  await loadProducts();
  renderTable();



   $('#search-bar').on('keypress', function(e) {
          if (e.key === 'Enter') {
              filterProducts(); // Pasa el valor actual del input a updateBar
          }
   });

   $('#search-button').on('click', function() {
           filterProducts();
   });

   $("#session-button").on("click", function() {
           $("#loginModal").show();
   });

   $(".close").on("click", function() {
           $("#loginModal").hide();
   });

     $(window).on("click", function(event) {
           if ($(event.target).is("#loginModal")) {
               $("#loginModal").hide();
           }
       });

});

async function loadProducts(){

      const request = await fetch('product/findAll', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },

      });
      products = await request.json();

      console.log(products);

}

function renderTable(){

          const tbody = document.querySelector('#TablaProd tbody');
          if (!tbody) {
               console.error('tbody no encontrado');
               return;
          }

          let finalHTML = "";
          if (products.length > 0){
             for (let i = currentPage * itemsPerPage; i < (currentPage + 1) * itemsPerPage && i < products.length; i++) {
                const product = products[i];
                console.log(product);
                let prodHTML = `
                  <tr>
                    <td>
                      <div class="product-row">
                        <div class="product-img">
                          <img src="${product.img}" alt="${product.name}">
                        </div>
                        <div class="product-details">
                          <div class="product-name">${product.name}</div>
                          <div class="product-desc">${product.desc}</div>
                          <div class="product-footer">
                            <div class="product-category">${product.category.join(', ')}</div>
                            <div class="product-price">${product.price.toFixed(2)}</div>
                          </div>
                        </div>
                      </div>
                    </td>
                  </tr>
                `;
                finalHTML = finalHTML + prodHTML;
              }
               tbody.outerHTML = finalHTML;
              //document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
          }


          //console.log(finalHTML);



}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        renderTable();
    }
}

function nextPage() {
    if ((currentPage + 1) * itemsPerPage < products.length) {
        currentPage++;
        renderTable();
    }
}

async function filterProducts(){

    var searchText = $('#search-bar').val();

    var selectedCategories = [];
    $('#category-menu input:checked').each(function() {
        selectedCategories.push($(this).val());
    });



    if(searchText!='' || selectedCategories != []){
        const request = await fetch(`product/filterProducts?searchText=${encodeURIComponent(searchText)}&categories=${encodeURIComponent(selectedCategories)}`, {
                method: 'GET',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                },

              });


        products = await request.json();

        console.log(products);
        renderTable();
    }else{
        loadProducts()
        renderTable();
    }


}

