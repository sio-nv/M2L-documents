package m2l.gestion.gestsallemob;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentListeBatiments extends Fragment {

    ListView lesBatiments;

    /**
     * adresse url de l'api: à initialiser avec l'adresse ip de votre serveur
     */
    private String urlApi;
    //list d'objets Salle
    private List<Salle> lesSalles = new ArrayList<Salle>();
    //liste de chaînes correspondant au nom des salles
    private List<String> nomSalles = new ArrayList<String>();



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.liste_batiments, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Récupération des salles en BDD , création de la la liste des salles */

        urlApi = "http://ip_a_preciser/GestSalleApiSon/public/admin/getSalles";

        lesBatiments = getActivity().findViewById(R.id.batlist);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());



        Log.w("FragementListeBatiments : ", urlApi);
        // Utilisa tion de 'com.android.volley:volley:1.1.1' (voir gradle.build (module)) pour lancer une URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //contiendra la réponse JSON
                        JSONArray sallesJSON;
                        try {

                            Log.w("FragementListeBatiments : ", "Requête effectuée");
                            //récupération de la réponse et rangement dans un tableau JSON
                            sallesJSON = new JSONArray(response);

                                Log.e("FragementListeBatiments", "Requête réussie. Affichage des bâtiments");

                                try {

                                    for (int i = 0; i < sallesJSON.length(); i++) {
                                        // création d'un JSONObject pour récupérer les informations de chaque salle
                                        JSONObject uneSalle = sallesJSON.getJSONObject(i);
                                        Salle tmp = new Salle();

                                        tmp.setId(uneSalle.getInt("id"));
                                        tmp.setCapacite(uneSalle.getInt("capacité"));
                                        tmp.setNom(uneSalle.getString("nom"));
                                        tmp.setEquipement(uneSalle.getString("equipement"));
                                        tmp.setServices(uneSalle.getString("services"));

                                        Log.e("FragementListeBatiments ", "nom salle : "+tmp.getNom());

                                        nomSalles.add(tmp.getNom());
                                        lesSalles.add(tmp);

                                        ((MainActivity)getActivity()).lesSalles =lesSalles ;
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, nomSalles);
                                        lesBatiments.setAdapter(adapter);

                                        lesBatiments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                String salle = lesBatiments.getItemAtPosition(position).toString();

                                                Snackbar.make(parent.getContext(),view,"Salle "+" "+position+" sélectionnée : "+salle,Snackbar.LENGTH_LONG);
                                                Log.e("Salle "," "+position+" sélectionnée : "+salle);

                                                NavHostFragment.findNavController(FragmentListeBatiments.this)
                                                       .navigate(R.id.ListeBatimentsVersFormulaire);


                                            }
                                        });

                                    }

                                    ((MainActivity)getActivity()).lesSalles = lesSalles;
                                    ((MainActivity)getActivity()).nomSalles = nomSalles;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                        } catch (JSONException e) {
                            Log.e("FragementListeBatiments", "Message (JSONException): " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {






            }
        });

        queue.add(stringRequest);



    }
}