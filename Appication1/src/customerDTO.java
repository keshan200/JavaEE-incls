public class customerDTO {

    String id;
    String name;
    String address;


    public customerDTO() {
    }


    public customerDTO(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*<script>

  const customerData = () =>{


    $.ajax({
      url:"http://localhost:8080/ijse/customer",
      method:"GET",

      success : (res)=>{

        $('#customerTableBody').empty();

        res.forEach((cus) => {
          let data = `
                    <tr>
                        <td>${cus.id}</td>
                        <td>${cus.name}</td>
                        <td>${cus.address}</td>
                    </tr>


                `;
          $('#customerTableBody').append(data);
        });

        console.log("success")
        console.log(res)
      },

      error : (error)=>{
        console.log("error")
      }


    })
  }


  $('#btnSV').click((e) => {

    e.preventDefault()

    const id = $('#customerId').val()
    const name = $('#customerName').val()
    const adrs = $('#customerAddress').val()

    const studentData = {
      id, name, adrs
    }


    console.log(studentData)

    $.ajax({
      url: 'http://localhost:8080/ijse/customer',
      method: 'POST',

      data:{
        id:id,
        name:name,
        adrs:adrs

      },

      success:(response)=>{
        console.log("saved")
        customerData();
      },

      error:(error)=>{
        console.log("err")
      }


    })

  })


  customerData();

</script>*/


    @Override
    public String toString() {
        return "customerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
