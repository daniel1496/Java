<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Product;
use App\Http\Controllers\Controller;


class ProductController extends Controller
{
       /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */

     // Controller will manage layout and ajax update items
    public function itemView(Product $product)
    {
        $product = $product->select('*')->paginate(10);
        $panddingItem = Item::where('status',0)->orderBy('order')->get();
    	$completeItem = Item::where('status',1)->orderBy('order')->get();
        

    	return view('dragAndDroppable',compact('panddingItem','completeItem','$product'));
    }

    public function updateItems(Request $request)
    {
        {
            $input = $request->all();
    
            foreach ($input['panddingArr'] as $key => $value) {
                $key = $key+1;
                Item::where('id',$value)->update(['status'=>0,'order'=>$key]);
            }
    
            foreach ($input['completeArr'] as $key => $value) {
                $key = $key+1;
                Item::where('id',$value)->update(['status'=>1,'order'=>$key]);
            }
    
            return response()->json(['status'=>'success']);
        }
    
    }

    public function index(Product $product)
    {
        //$product = Product::latest()->paginate(5);
       // $product = Product::latest();

        //return view('item.index', compact('product'))
           // ->with('i', (request()->input('page', 1) - 1) * 5);
           try {
            $products = $product->select('*')->paginate(10);
    
            return view('item.index', ['products' => $products]);

        } 
        catch(\Kyslik\ColumnSortable\Exceptions\ColumnSortableException $e) 
        {
            dd($e);
        }         
    }
    /** Form to create a new resource 
     * @return \Illuminate\Http\Response
    */
    
    public function create()
    {
        return view('item.create');
    }

    /**
     * Store newly created resource in storage.
     * 
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $request->validate([
            'id' => 'required',
            'title' => 'required',
            'price' => 'required',
            'status' => 'required'
        ]);

        Product::create($request->all());
        return redirect()->route('product.index')->with('success', 'Item created Successfully.');
    }
      /**
     * Display the specified resource.
     *
     * @param  \App\Models\Product  $product
     * @return \Illuminate\Http\Response
     */

     public function show(Product $product)
     {
         return view('item.show', compact('Product'));
     }
      /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\Product  $product
     * @return \Illuminate\Http\Response
     */

     public function edit(Product $product)
     {
         return view('item.edit', compact('product'));
     }

     /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Product  $product
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Product $product)
    {
        $request->validate([
            'id' => 'required',
            'title' => 'required',
            'price' => 'required',
            'status' => 'required'
        ]);
        $product->update($request->all());

        return redirect()->route('product.index')
            ->with('success', 'Items updated successfully');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Product  $product
     * @return \Illuminate\Http\Response
     */
    public function destroy(Product $product)
    {
        $product->delete();

        return redirect()->route('dashboard')
            ->with('success', 'Item deleted successfully');
    }

}

  