$("#btnSave").click(()=>{

    let id = $('#id').val();
    let name = $('#name').val();
    let address = $('#address').val();

    $.ajax({
        url: `http://localhost:8080/untitled_Web_exploded/m`,
        method: "POST",


        headers:{
            id : id,
            name : name,
            address : address,
            contentType: "application/json"

        },

        data: JSON.stringify({
            id: id,
            name: name,
            address:address
        }),

        success:(res)=>{
            fetchData()
            clearFields()

            Swal.fire({
                title: "Added!",
                icon: "success",
                draggable: true
            });
        },
        error:(err)=>{
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Can't Add Customer",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        }
    })



})
