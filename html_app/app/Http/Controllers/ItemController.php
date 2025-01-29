<?php
namespace App\Http\Controllers;

use App\Models\Item;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Models\Product;

class ItemController extends Controller
{
    /**
     * Display a listing of the items.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $items = Item::latest()->paginate(5);

        return view('item.index', compact('items'))
            ->with('i', (request()->input('page', 1) - 1) * 5);
    }


    /**
     * Show the form for creating a new stock.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('item.create');
    }



    /**
     * Store a newly created items in storage.
     *
     * @param  \Illuminate\Http\Request  $request
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

        Item::create($request->all());

        return redirect()->route('item.index')
            ->with('success', 'new stock added successfully.');
    }




    /**
     * Display the specified stock.
     *
     * @param  \App\Models\Item  $item
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //return view('items.show', compact('item'));

          //return view('items.show', [
          //  'items' => item::findOrFail($item)
         //       ]);

       $item= Item::findOrFail($id);

       return view('item.show', compact('item'));
    }




    /**
     * Display the form for editing the Medication.
     * @param  int  $id
     * @param  \App\Models\Item  $item
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
       // return view('item.edit', compact('item'));

       $item = Item::findOrFail($id);

        return view('item.edit', compact('item'));
    }



    /**
     * Update the Medication in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Item  $med
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Item $item)
    {
        $request->validate([
            'id' => 'required',
            'title' => 'required',
            'price' => 'required',
            'status' => 'required'
        ]);
        $item->update($request->all());

        return redirect()->route('item.index')
            ->with('success', 'Stock updated successfully');
    }



    /**
     * Remove the Medication from storage.
     *
     * @param  \App\Models\Item  $med
     * @return \Illuminate\Http\Response
     */
    public function destroy(Item $item)
    {
        $item->delete();

        return redirect()->route('Item.index')
            ->with('success', 'Stock deleted successfully');
    }

    public function list()
    {
       //if (!empty(request()->get('username'))) {
          
              $item = Item::ignoreRequest('perpage')->filter()->with('items')->paginate(request()->get('perpage'),['*'],'page');

         // } else {
            //  $item = Medicine::filter(
              //  ['location' => ['Cannock']]           
               // )->with('posts')->paginate(10,['*'],'page');
          }
    }



