/**
 * Created by Nikita on 11/06/2017.
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;


/**
 * Classe che gestisce la finestra di input dei valori e i due grafici
 */
public class InputController {
    //valori di default
    private int def_a = new Integer(10000);
    private int def_b = new Integer(10000);
    private int def_c = new Integer(10000);
    private int def_d = new Integer(10000);
    private int def_a1 = new Integer(15);
    private int def_b1 = new Integer(20);
    private int def_c1 = new Integer(3);
    boolean bool = true;
    static Population p;
    private List<int[]> popArray = new ArrayList<>();

    @FXML
    private TextField tf_morigerati;

    @FXML
    private TextField tf_avventurieri;

    @FXML
    private TextField tf_prudenti;

    @FXML
    private TextField tf_spregiudicate;

    @FXML
    private TextField tf_costocorteggiamento;

    @FXML
    private TextField tf_costocrescita;

    @FXML
    private TextField tf_premiogenerazionefigli;

    @FXML
    void onAvviaClick(ActionEvent event) {
        //Prendo le stringhe dentro i campi compilati
        String a = tf_morigerati.getText();
        String b = tf_avventurieri.getText();
        String c = tf_prudenti.getText();
        String d = tf_spregiudicate.getText();
        String a1 = tf_premiogenerazionefigli.getText();
        String b1 = tf_costocrescita.getText();
        String c1 = tf_costocorteggiamento.getText();
        int aa, bb, cc, dd, aa1, bb1, cc1;
        //Trasformo le stringhe in int se non sono stringhe vuote
        if (!(a.equals("") || b.equals("") || c.equals("") || d.equals("") || a1.equals("") || b1.equals("") || c1.equals(""))) {
            aa = Integer.parseInt(a);
            bb = Integer.parseInt(b);
            cc = Integer.parseInt(c);
            dd = Integer.parseInt(d);
            aa1 = Integer.parseInt(a1);
            bb1 = Integer.parseInt(b1);
            cc1 = Integer.parseInt(c1);
        } else { //altrimenti mando in input i valori di default
            aa = def_a;
            bb = def_b;
            cc = def_c;
            dd = def_d;
            aa1 = def_a1;
            bb1 = def_b1;
            cc1 = def_c1;
        }
        //se si inseriscono valori negativi per le popolazioni, il programma partirà con valori di default
        if (aa < 0 || bb < 0 || cc < 0 || dd < 0) {
            aa = def_a;
            bb = def_b;
            cc = def_c;
            dd = def_d;
            aa1 = def_a1;
            bb1 = def_b1;
            cc1 = def_c1;
        }
        p = new Population(aa,bb,dd,cc);
        p.setValues(aa1,bb1,cc1);

        //grafico a torta
        Graphics.PieChartDemo2 pie = new Graphics.PieChartDemo2("Population");
        pie.pack();
        RefineryUtilities.positionFrameOnScreen(pie,0.1,0.4);
        pie.setVisible(true);

        //grafico a linee
        final Graphics.DynamicLineAndTimeSeriesChart demo=new Graphics.DynamicLineAndTimeSeriesChart("Population");
        demo.pack();
        RefineryUtilities.positionFrameOnScreen(demo,0.9,0.4);
        demo.setVisible(true);

        /*per una migliore visibilità dell'evoluzione nel grafico, la funzione che fa evolvere la popolazione è chiamata
        ogni 200 iterazioni */
        int n = 1;
        boolean stabilized = false;
        boolean clear = false;
        while(bool) {
            if (n == 0) {
                p.evolve();
                if (!stabilized) {
                    if (popArray.size() < 20) {
                        popArray.add(p.currentPopulation());
                    } else if (popArray.size() >= 20) {
                        popArray.remove(0);
                        popArray.add(p.currentPopulation());
                        boolean stable = p.checkForStability(popArray);
                        if (stable) {
                            Random r = new Random();
                            int i = r.nextInt(3);
                            switch (i) {
                                case 0: {
                                    p.incrementPopulation(50000, "M");
                                    out.println("La popolazione era stabile. Ho aggiunto 50000 Morigerati, vediamo come va...");
                                    stabilized = true;
                                    clear = true;
                                    break;
                                }
                                case 1: {
                                    p.incrementPopulation(50000, "P");
                                    out.println("La popolazione era stabile. Ho aggiunto 50000 Prudenti, vediamo come va...");
                                    stabilized = true;
                                    clear = true;
                                    break;
                                }
                                case 2: {
                                    p.incrementPopulation(50000, "A");
                                    out.println("La popolazione era stabile. Ho aggiunto 50000 Avventurieri, vediamo come va...");
                                    stabilized = true;
                                    clear = true;
                                    break;
                                }
                                case 3: {
                                    p.incrementPopulation(50000, "S");
                                    out.println("La popolazione era stabile. Ho aggiunto 50000 Spregiudicate, vediamo come va...");
                                    stabilized = true;
                                    clear = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (stabilized) {
                    if (clear) {
                        popArray.clear();
                        clear = false;
                    }
                    if (popArray.size() < 20) {
                        popArray.add(p.currentPopulation());
                    } else if (popArray.size() >= 20) {
                        popArray.remove(0);
                        popArray.add(p.currentPopulation());
                        boolean stable = p.checkForStability(popArray);
                        if (stable) {
                            out.println("Di nuovo stabili! :)");
                            stabilized = false;
                        }
                    }
                }
            }
            Graphics.PieChartDemo2 demo2=new Graphics.PieChartDemo2("Popolazione");
            pie.setContentPane(demo2.getContentPane());
            pie.setVisible(true);
            n = (n+1)%200;
        }
    }

    /**
     * Metodo chiamato dai grafici per ottenere le percentuali relative dei tipi
     * @param n l'array d interi che rappresenta il numero di individui per ciascun tipo
     * @return un array di double contenente le percentuali relative dei tipi
     */
    public static double[] faiIlPrint(int[] n) {
        double[] d = new double[4];
        d[0] = ((double)n[2])/((double)n[2]+(double)n[3]); //prudenti
        d[1] = ((double)n[0])/((double)n[0]+(double)n[1]); //morigerati
        d[2] = ((double)n[1])/((double)n[1]+(double)n[0]); //avventurieri
        d[3] = ((double)n[3])/((double)n[3]+(double)n[2]); //spregiudicate
        return d;
    }

}
