let products = [];
let events = [];
let currentPage = 0;
const itemsPerPage = 10;

$(document).ready(async function() {

  await loadProducts();
  await loadEvents();
  renderTable();
  renderTableEvents();


   $('#search-bar').on('keypress', function(e) {
          if (e.key === 'Enter') {
              filterProducts(); // Pasa el valor actual del input a updateBar
          }
   });

   $('#search-button').on('click', function() {
           filterProducts();
   });

   $('#send-event').on('click', function() {
              sendEvent();
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

      console.log("load products");

}

async function loadEvents(){

      const request = await fetch('event/findAll', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },

      });
      response = await request.json();
      events = response.content;
      console.log("load events");

}

function renderTableEvents(){
    const tbodyE = document.querySelector('#TablaEvent tbody');

    if (!tbodyE) {
       console.error('tbodyE no encontrado');
       return;
    }

    let finalHTML = "";
    console.log(events)

        if (events.length > 0){
         for (let y =0; y <  events.length; y++) {
            const event = events[y];

            let eventHTML = `
              <tr>
                <td>
                  <div class="event-row">
                     <div class="event-dataini">${event.date_ini}</div>
                     <div class="event-datafi">${event.date_fi}</div>
                     <div class="event-category">${event.categoria}</div>
                     <div><button data-id="${y}" onclick="deleteEvent(${y})"> X </button></div>
                  </div>
                </td>
              </tr>
            `;
            finalHTML = finalHTML + eventHTML;
         }

         tbodyE.innerHTML = finalHTML;
         //document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
        }else{
        tbodyE.innerHTML = finalHTML;
        }

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
               tbody.innerHTML = finalHTML;
              //document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
          }else{
            tbodyE.innerHTML = finalHTML;
          }


          //console.log(finalHTML);



}

async function deleteEvent(id){
    var event = events[id];

    const response = await fetch('/event/delete', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
    });

    await loadProducts();
    await loadEvents();
    await sleep(500);
    renderTable();
    renderTableEvents();

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




    var categ = $('#categoriy-search').val();
    var selectedCategories = [];
    if(categ != "None"){
        selectedCategories.push(categ);
    }
    console.log(categ);

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


async function sendEvent(){

    var day_ini = $('#day_ini').val();
    var month_ini = $('#month_ini').val();
    var year_ini = $('#year_ini').val();

    var day_fi = $('#day_fi').val();
    var month_fi = $('#month_fi').val();
    var year_fi = $('#year_fi').val();

    var valid = true;

    if(year_ini>1000 && year_ini < 9999 && year_fi>1000 && year_fi < 9999){
        if(month_ini==01 || month_ini==03 || month_ini==05 || month_ini==07 || month_ini==08 || month_ini==10 || month_ini==12 ){
            if(day_ini < 0 && day_ini > 31){
                valid = false;
                console.log("Valid: " + valid);
            }
        }else if(month_ini==04 || month_ini==06 || month_ini==09 || month_ini==11){
            if(day_ini < 0 && day_ini > 30){
                valid = false;
                console.log("Valid: " + valid);
            }
        }else if(month_ini==02){
            if(day_ini < 0 && day_ini > 28){
                valid = false;
                console.log("Valid: " + valid);
            }
        }

        if(month_fi==01 || month_fi==03 || month_fi==05 || month_fi==07 || month_fi==08 || month_fi==10 || month_fi==12 ){
             if(day_fi < 0 && day_fi > 31){
                valid = false;
                console.log("Valid: " + valid);
             }
        }else if(month_fi==04 || month_fi==06 || month_fi==09 || month_fi==11){
            if(day_fi < 0 && day_fi > 30){
                valid = false;
                console.log("Valid: " + valid);
            }
        }else if(month_fi==02){
            if(day_fi < 0 && day_fi > 28){
                valid = false;
                console.log("Valid: " + valid);
            }
        }

        if(year_ini > year_fi){
            valid=false;
            console.log("Valid: " + valid);
        } else if(month_ini > month_fi){
            valid=false;
            console.log("Valid: " + valid);
        } else if(day_ini > day_fi){
            valid=false;
            console.log("Valid: " + valid);
        }

    }else{
        valid=false;
        console.log("Valid: " + valid);
        console.log(year_ini);
        console.log(year_fi);
    }

    if(valid){


        var data_ini = String(year_ini) +"-"+ String(month_ini) +"-"+ String(day_ini);
        var data_fi = String(year_fi) +"-"+ String(month_fi) +"-"+ String(day_fi);
        var categoriaEvent = $('#categoriy-event').val();

        var event = {
            categoria: categoriaEvent,
            date_ini: data_ini,
            date_fi: data_fi
        }

        const response = await fetch('/event/insert', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(event)
        });
    }else{

    }

    await loadEvents();
    await loadProducts();

    await sleep(500);

    renderTable();
    renderTableEvents();






}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

