<script>
  window.onload = function(){
    var a = ['\\','&lt;','&gt;','(',')','{','}','[',']','*','.',',','+','-','\'','!','£','$','%','^'];
    var z = document.getElementsByTagName('pre');
    for(var x = 0; x < z.length; x++){
      for(var i = 0; i < a.length; i++){
        z[x].innerHTML = z[x].innerHTML.split(a[i]).join('<font color="#F' + (i % 16).toString(16) + '0">' + a[i] + '</font>');
      }
    }
  }
</script>
