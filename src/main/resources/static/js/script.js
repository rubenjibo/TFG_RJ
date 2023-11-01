let products = [];
let currentPage = 0;
const itemsPerPage = 10;

$(document).ready(async function() {

  await loadProducts();
  renderTable();
});

async function loadProducts(){

      const request = await fetch('prod/findAll', {
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
          if (products.content && products.content.length > 0){
             for (let i = currentPage * itemsPerPage; i < (currentPage + 1) * itemsPerPage && i < products.content.length; i++) {
                const product = products.content[i];
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
    if ((currentPage + 1) * itemsPerPage < products.content.length) {
        currentPage++;
        renderTable();
    }
}