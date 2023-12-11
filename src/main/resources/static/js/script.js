let products = [];
let events = [];
let prios = [];
let eventsProv = [];
let priosProv = [];
let currentPage = 0;
const itemsPerPage = 10;
let username= "";
let userpwd= "";
let adminMode=0;


$(document).ready(async function() {

  await loadProducts();
  await loadEvents();
  await loadPrios();

  renderTable();
  renderTableEvents();
  renderTablePrios();
  loadSelectPrios()

   $('#search-bar').on('keypress', function(e) {
          if (e.key === 'Enter') {
              filterProducts(); // Pasa el valor actual del input a updateBar
          }
   });

   $('#search-button').on('click', function() {
           filterProducts();
   });

    $('#login-button').on('click', function() {
              $("#loginModal").hide();
              login();

    });

    $('#logout-button').on('click', function() {
  logout();

    });

   $('#send-event').on('click', function() {
              sendEvent();
   });

   $('#send-prio').on('click', function() {
              sendPrio();
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

      if(adminMode == 0){
        const request = await fetch('product/findAll', {
            method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },

        });
        products = await request.json();

      }else if(adminMode == 1){

        const requestData = {
            eventsProv: eventsProv,
            priosProv: priosProv
        };
        console.log(requestData);
        const request = await fetch('product/findAllProv', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });
        products = await request.json();


      }




      console.log("load products");
      console.log(products);

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

async function loadPrios(){

      const request = await fetch('prio/findAll', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },

      });
      response = await request.json();
      prios = response.content;
      console.log("load prios");

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
            tbody.innerHTML = finalHTML;
          }


          //console.log(finalHTML);



}



function renderTableEvents(){
    const tbodyE = document.querySelector('#TablaEvent tbody');

    if (!tbodyE) {
       console.error('tbodyE no encontrado');
       return;
    }

    let finalHTML = "";
    console.log(events)

    if(eventsProv.length > 0){

        for(let i=0; i < eventsProv.length; i++){

            const eventProv = eventsProv[i];


            let eventProvHTML = `
              <tr>
                <td>
                  <div class="event-row">
                     <div class="event-dataini">${eventProv.date_ini}</div>
                     <div class="event-datafi">${eventProv.date_fi}</div>
                     <div class="event-category">${eventProv.categoria}</div>
                     <div><button data-id="${i}" onclick="saveEventProv(${i})"> Save </button></div>
                     <div><button data-id="${i}" onclick="deleteEventProv(${i})"> X </button></div>
                  </div>
                </td>
              </tr>
            `;
            finalHTML = finalHTML + eventProvHTML;
        }
    }


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


     //document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
    }
    tbodyE.innerHTML = finalHTML;


}

function renderTablePrios(){
    const tbodyE = document.querySelector('#TablaPrio tbody');

    if (!tbodyE) {
       console.error('tbodyE no encontrado');
       return;
    }

    let finalHTML = "";


    if(priosProv.length > 0){

        for(let i=0; i < priosProv.length; i++){

            const prioProv = priosProv[i];


            let eventProvHTML = `
              <tr>
                  <td>
                    <div class="prio-row">
                       <div class="prio-row-dates">
                          <div class="prio-dataini">${prioProv.date_ini}  </div>
                          <div class="prio-dataini">${prioProv.date_fi}</div>

                       </div>

                       <div class="prio-row-numbers">
                           <div class="prio-product">ID: ${prioProv.product}  </div>
                           <div class="prio-position">Index: ${prioProv.position}</div>
                           <div><button data-id="${i}" onclick="savePrioProv(${i})"> Save </button></div>
                           <div><button data-id="${i}" onclick="deletePrioProv(${i})"> X </button></div>
                       </div>

                    </div>
                  </td>
              </tr>
            `;
            finalHTML = finalHTML + eventProvHTML;
        }
    }


    if (prios.length > 0){
     for (let y =0; y <  prios.length; y++) {
        const prio = prios[y];

        let prioHTML = `
          <tr>
            <td>
              <div class="prio-row">
                 <div class="prio-row-dates">
                    <div class="prio-dataini">${prio.date_ini}  </div>
                    <div class="prio-dataini">${prio.date_fi}</div>

                 </div>

                 <div class="prio-row-numbers">
                     <div class="prio-product">ID: ${prio.product}  </div>
                     <div class="prio-position">Index: ${prio.position}</div>
                     <div><button data-id="${y}" onclick="deletePrio(${y})"> X </button></div>
                 </div>

              </div>
            </td>
          </tr>
        `;
        finalHTML = finalHTML + prioHTML;
     }

     tbodyE.innerHTML = finalHTML;
     //document.querySelector('#TablaProd tbody').outerHTML=finalHTML;
    }else{
    tbodyE.innerHTML = finalHTML;
    }

}

function deleteEventProv(index){

    eventsProv.splice(index,1);
    loadProducts();
    renderTableEvents();
    renderTable();

}

function deletePrioProv(index){

    priosProv.splice(index,1);
    loadProducts();
    renderTablePrios();
    renderTable();

}

async function saveEventProv(index){

        var eventSend = eventsProv[index];
        eventsProv.splice(index,1);

        const base64Credentials = btoa(username + ":" + userpwd);
        const response = await fetch('/event/insert', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + base64Credentials
            },
            body: JSON.stringify(eventSend)
        });



        await loadProducts();
        await loadEvents();

        renderTable();
        renderTableEvents();

}



async function deleteEvent(id){
    var event = events[id];

    const base64Credentials = btoa(username + ":" + userpwd);

    const response = await fetch('/event/delete', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + base64Credentials
        },
        body: JSON.stringify(event)
    });

    await loadProducts();
    await loadEvents();

    renderTable();
    renderTableEvents();

}

async function deletePrio(id){
    var prio = prios[id];
    const base64Credentials = btoa(username + ":" + userpwd);
    const response = await fetch('/prio/delete', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + base64Credentials
        },
        body: JSON.stringify(prio)
    });

    await loadProducts();
    await loadPrios();

    renderTable();
    renderTablePrios();

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

    var valid = chekDates(day_ini,month_ini,year_ini,day_fi,month_fi,year_fi);

    if(valid){


        var data_ini = String(year_ini) +"-"+ String(month_ini) +"-"+ String(day_ini);
        var data_fi = String(year_fi) +"-"+ String(month_fi) +"-"+ String(day_fi);
        var categoriaEvent = $('#categoriy-event').val();

        var event = {
            categoria: categoriaEvent,
            date_ini: data_ini,
            date_fi: data_fi
        }

        eventsProv.push(event);


    }else{

    }


    await loadProducts();

    renderTable();
    renderTableEvents();






}

async function sendPrio(){

    var day_ini = $('#day_ini_prio').val();
    var month_ini = $('#month_ini_prio').val();
    var year_ini = $('#year_ini_prio').val();

    var day_fi = $('#day_fi_prio').val();
    var month_fi = $('#month_fi_prio').val();
    var year_fi = $('#year_fi_prio').val();



    var valid = chekDates(day_ini,month_ini,year_ini,day_fi,month_fi,year_fi);
    console.log(valid);
    if(valid){


        var data_ini = String(year_ini) +"-"+ String(month_ini) +"-"+ String(day_ini);
        var data_fi = String(year_fi) +"-"+ String(month_fi) +"-"+ String(day_fi);

        var productId = $('#prod_prio').val();
        var productIndex = $('#position_prio').val() - 1;

        console.log(data_ini);
        console.log(data_fi);
        var prioritat = {
            product: productId,
            position: productIndex,
            date_ini: data_ini,
            date_fi: data_fi
        }

        priosProv.push(prioritat);


    }else{

    }


    await loadProducts();

    renderTable();
    renderTablePrios();




}

async function savePrioProv(index){

    var prioSend = priosProv[index];
    priosProv.splice(index,1);

    const base64Credentials = btoa(username + ":" + userpwd);
    const response = await fetch('/prio/insert', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + base64Credentials
        },
        body: JSON.stringify(prioSend)
    });


    await loadPrios();
    await loadProducts();

    renderTable();
    renderTablePrios();


}


function chekDates(day_ini,month_ini,year_ini,day_fi,month_fi,year_fi){

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

    return valid;
}



function loadSelectPrios(day_ini,) {

    var products_aux = Array.from(products);

    products_aux.sort(function(a, b) {
        return a.id - b.id;
    });

    $.each(products_aux, function(index, product) {
        $('#prod_prio').append(new Option(`${product.id} - ${product.name}`, product.id));
        $('#position_prio').append(new Option(`${product.id}`, product.id));

    });



}

async function login(){
    var name = $('#username').val();
    var pwd = $('#password').val();

    var loginData = {
        name: name,
        pwd: pwd
    }


    const response = await fetch('/user/checkUser', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',

        },
        body: JSON.stringify(loginData)
    });


    const responseData = await response.json();
    console.log("Login: " + responseData);
    if(responseData){
        //display true
        username = name;
        userpwd = pwd;
        adminMode = 1;
        $(".admin-containers").css('display', 'flex');
        $("#session-button").css('display', 'none');
        $("#logout-button").css('display', 'inline-block');

    }else{
         alert("L'usuari o la contrasenya no son correctes");
    }
}

function logout(){

    username="";
    userpwd="";
    adminMode=0;

    $(".admin-containers").css('display', 'none');
    $("#session-button").css('display', 'inline-block');
    $("#logout-button").css('display', 'none');


}


