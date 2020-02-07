var floor = document.querySelector('#floor');
var paths= floor.querySelectorAll('.floor_image a');
var links = floor.querySelectorAll('.floor_list a');
var arrayPath = [];


if(NodeList.prototype.forEach ===undefined){
    NodeList.prototype.forEach = function (callback){
        [].forEach.call(this,callback)
    }
}

updateFromAndroid = function(array){
   floor.querySelectorAll('.is-active').forEach(function(item){
      item.classList.remove('is-active');
   })
    x=document.getElementById("whichpart");
    x.innerHTML ="";
    array.forEach(function(item){
        DOMid=document.querySelector('#'+item);
        activeAreaPath(DOMid);
    })
}


paths.forEach(function(path){
    path.addEventListener('click',function(e){
        var id = this.id;
        console.log(id);
        activeAreaClick(id);
    })
})

links.forEach(function(link){
    link.addEventListener('click',function(){
        var id = this.id.replace('list-','');
        activeAreaClick(id);
    })
})

var activeAreaClick = function (id){
   floor.querySelectorAll('.is-active').forEach(function(item){
      item.classList.remove('is-active');
  })

    if(id!==undefined){
       // document.querySelector('#list-'+id).classList.add('is-active');
       document.querySelector('#'+id).classList.add('is-active');
       x=document.getElementById("whichpart");
       x.innerHTML = id;
    }

}

var activeAreaPath = function(DOMid){
     if(id!==undefined){
         DOMid.classList.add('is-active');
         x=document.getElementById("whichpart");
         x.innerHTML = x.innerHTML+" --> "+id;
     }

 }



/*
floor.addEventListener('mouseover',function(){
    activeArea();
})
*/