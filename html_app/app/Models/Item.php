<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;


class Item extends Model
{
    use HasFactory;
    //use Sortable;
    //use Filterable;

    protected $fillable =[
        'id' => 'required',
        'title' => 'required',
        'price' => 'required',

    ];

    //Changes primary key from default = id
    protected $primaryKey = 'id';

    public $timestamps = false;

    //Assigns stock as a child element of ItemPack
    public function itemPack()
    {
        return $this->belongsTo(ItemPack::class);
    }


    //Returns the itemPack of the given stock
    public function getItemPack()
    {
        $item = Item::find(1);

        return $this->item->pack_id;

    }

   
}