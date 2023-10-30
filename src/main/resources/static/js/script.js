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
      if (products.content && products.content.length > 0){
        for (let product of products.content) {
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
      }


        console.log(finalHTML);

      document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
}