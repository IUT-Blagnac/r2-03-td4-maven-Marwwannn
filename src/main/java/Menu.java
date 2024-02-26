
/**
 * Exemple de menu
 *
 * 
 */

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.text.*;

public class Menu
{

    //texte du menu
    public static String [][] promo = null;
    public static int nbItemMenu = 3;
    
    
    public static String texteMenu = "\n/**********************************************/\n"+"\t\t\tSelectionner\n\t\t1 - un etudiant sauf dernier selectionne\n"
        +"\t\t2 - un etudiant dans la promo\n"+"\t\t3 - affiche la promo\n\t\t0 - Quitter\n"+"/**********************************************/\n\n";

    /**  permet de retourner une valeur entiere saisie au clavier comprise entre pfBorneInf et pfBorneSup
     *@param pfBorneSup In la borne sup
     *@param pfBorneInf In la borne inf
     *@param pfMessage In message à afficher
     *@return valeur entiere comprise entre pfBorneInf et pfBorneSup
     **/
    public static int saisieIntC (int pfBorneInf,  int pfBorneSup, String pfMessage){
        int valeur;
        Scanner clavier = new Scanner(System.in) ;
        System.out.print(pfMessage); 

        valeur=clavier.nextInt();
        while (valeur<pfBorneInf || valeur>pfBorneSup){
            System.out.println(pfMessage);
            System.out.print("Erreur ! Donnez une valeur comprise " + pfBorneInf +" et "+pfBorneSup+ "?");
            valeur=clavier.nextInt();
        }
        return valeur;
    }

    /**  traite le choix 1
     *@param
     * promo IN OUT tableaux à 2 entré
     * nbE entier Nombre d'éleves 
     * Dernier andrewTest tableaux contenant les dernirs tirages
     * test IN OUT tableaux pour vérifier si l'étudiant de l'indice est passé 
     *@return un étudiant correspondant aux critères
     **/
    public static String [] traiterChoix1(String [][]promo, int nbE, boolean[]test, String[]dernière){
        boolean Verif = false;
        /* ça fait defiler le tableau et s'arrete si il y a un true donc si un eleve est passer */
        for (int i = 0;i<nbE;i++){
            if(test[i]==false){
                Verif=true;
                break;
            }
        }
        
        if(Verif==false){
            for(int i = 0;i<nbE;i++){
                test[i]=false;
            }
        }
        /* tire un indice entre 0 et le nombre d'éleve */
        int tirage = Tirage.tirageEleve(promo);
        /* vérifie que l'élève est passé */
        while (test[tirage] == true || (promo[tirage][ 0] == dernière[0] &&  (promo[tirage][1] == dernière[1]))){
            tirage = Tirage.tirageEleve(promo); 
        }
        /* mettre true dans le tableau test a l'indice tirage */
        test[tirage] = true;
        /* afficher le prénom de l'élève a l'indice tirage */
        dernière[0] = promo[tirage][0];
        /* afficher le nom de l'élève a l'indice tirage */
        dernière[1] = promo[tirage][1];
        /* Affiche le nom et prenom de l'eleve tirer au sort */
        return dernière;
    }

    
    /**  traite le choix 2
     *@param 
     *promo IN OUT tableaux à 2 entrés
     *@return un étudiant correspondant aux critères
     **/
    public static String [] traiterChoix2(String[][] promo){
        String etu[] = new String[2];
        /* tableau contenant les noms et prenoms des éleves */
        int indice= Tirage.tirageEleve(promo);
        etu[0] = promo[indice][0];
        /* prénom de l'éleve */
        etu[1] = promo[indice][1];
        /* nom de l'éleve */
        return etu;
    }

    /**  affiche le menu et exécute les choix...
     *@param vous pouvez en ajouter 
     *@return un étudiant correspondant aux critères
     **/
    public static void testMenu(){
        /* Commande choisir touche par l'utilisateur */
        int choixUtilisateur ;
        /* Nombre éleve égal a la taille du tableau promo  */
        int nbE = promo.length;
        /* nbE contien la taille de la table promo */
        boolean test[] = new boolean[nbE];
        /* tableaux de boolean*/
        String dernière[] = new String[2];
        /* tableau des noms et prenoms des derniers éleves passé */
        String etu[];
        do
        {
            System.out.println(texteMenu);
            choixUtilisateur = saisieIntC ( 0, nbItemMenu, "Choisir ");

            try {
                switch (choixUtilisateur)
                {
                    case 3 :
                    //ecrire un sp
                    /* afficher promo */
                    for (int i=0;i<ListeEtudiants.nbEtudiant(promo);i++){

                        System.out.println("etu : "+(i+1) +"\t"+promo [i][ 0]+"\t" +promo [i][1]);
                    }

                    break ;
                    case 2 :
                    //ecrire un sp
                    etu = traiterChoix2(promo) ;
                    /* appel fonction qui choisi un éleve dans la promo */
                    System.out.println(etu[0] + " "+ etu[1]);
                    break ;
                    case 1 :
                    etu = traiterChoix1(promo,nbE,test,dernière) ;
                    /* appel fonction qui un etudiant sauf dernier selectionne */
                    System.out.println(etu[0] + " "+ etu[1]);
                    
                    break ; 
                    case 0 :
                    System.out.println ("AU REVOIR ^^ ...   ...\n");

                    break ;
                    default :
                    System.out.println("\n\n\nBIZZZZARRE ... \n\n\n");
                    break;
                } 
            }catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
        while (choixUtilisateur != 0);

    }

    public static void main(String arguments[]) {
        try {
            promo = ListeEtudiants.getListe("listenomssansaccent.csv", ","); //appel du sous programme précédé du nom de la classe où elle est définie
            System.out.println("Il y a : " + ListeEtudiants.nbEtudiant(promo) + " etudiants"); 
            testMenu();

        }
        catch (Exception e) {  
            System.out.println("Erreur : "+e.getMessage());

        } 

     
    } // fin main
}

