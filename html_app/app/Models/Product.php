<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Product extends Model
{
    use HasFactory;
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'id', 'title', 'price', 'status'
    ];
    protected $table = 'products';
    public $timestamps = false;

    protected $casts = [
        'price' => 'float'

    ];
}