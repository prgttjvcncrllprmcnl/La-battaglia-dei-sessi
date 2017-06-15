import java.util.*;

/**
 *
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public class Population implements Evolution {

    private int mor, avv, pru, spr, a, b, c;

    /**
     * Enum dei tipi ammessi.
     * M = Morigerato
     * A = Avventuriero
     * P = Prudente
     * S = Spregiudicata
     */
    public enum Type{
        M, A, P, S
    }

    /**
     * Costruttore di una popolazione
     * @param mor numero di morigerati
     * @param avv numero di avventurieri
     * @param spr numero di spregiudicate
     * @param pru numero di prudenti
     */
    public Population(int mor, int avv, int spr, int pru) {
        this.mor = mor;
        this.avv = avv;
        this.pru = pru;
        this.spr = spr;
    }

    /**
     * Metodo che dimezza la popolazione non appena uno dei tipi supera le 100000 unità (per evitare numeri troppo grandi)
     */
    public void killpercent() {
        if (mor >= 100000 || avv >= 100000 || pru >= 100000 || spr >= 100000) {
            mor = mor/2;
            avv = avv/2;
            spr = spr/2;
            pru = pru/2;
        }
    }

    @Override
    public void evolve() {
        killpercent();
        int mor_n = get30Percent(mor);
        int avv_n = get30Percent(avv);
        int pru_n = get30Percent(pru);
        int spr_n = get30Percent(spr);

        List<Integer> l = new ArrayList<>();
        l.add(mor_n); l.add(avv_n); l.add(pru_n); l.add(spr_n);
        Random r = new Random();
        int n = r.nextInt(4); // per vedere chi inizia a scegliere
        int sum = mor_n+avv_n+pru_n+spr_n;
        for (int i = 0; i < sum; i++) {
            if (l.get(n) > 0) {
                int coin = r.nextInt(2);
                switch (n) {
                    case 0: { //morigerato
                        int coin2 = r.nextInt(2);
                        boolean change_strategy = changeStrategy(Type.M);
                        if (!change_strategy) {
                            if (coin == 0) { //sceglie la prudente
                                if (pru_n != 0 && coin2 == 0) {
                                    createFamily(Type.M, Type.P);
                                    mor_n--;
                                    pru_n--;
                                } else {
                                    mor_n--;
                                }
                            } else if (coin == 1) { //sceglie la spregiudicata
                                if (spr_n != 0 && coin2 == 0) {
                                    createFamily(Type.M, Type.S);
                                    mor_n--;
                                    spr_n--;
                                } else {
                                    mor_n--;
                                }
                            }
                        } else { //cambia strategia (diventa avventuriero)
                            mor--;
                            avv++;
                            mor_n--;
                            avv_n++;
                            if (coin == 0) { //gli capita la prudente
                                avv_n--;
                            } else if (coin == 1) { //gli capita la spregiudicata
                                if (spr_n != 0 && coin2 == 0) {
                                    createFamily(Type.A, Type.S);
                                    avv_n--;
                                    spr_n--;
                                } else {
                                    avv_n--;
                                }
                            }
                        }
                        break;
                    }
                    case 1: { //avventuriero
                        int coin2 = r.nextInt(2);
                        boolean change_strategy = changeStrategy(Type.A);
                        if (!change_strategy) {
                            if (coin == 0) { //sceglie la spregiudicata
                                if (spr_n != 0 && coin2 == 0) {
                                    createFamily(Type.A, Type.S);
                                    avv_n--;
                                    spr_n--;
                                } else {
                                    avv_n--;
                                }
                            } else if (coin == 1) { //sceglie la prudente
                                avv_n--;
                            }
                        } else { //cambia strategia (diventa morigerato)
                            avv--;
                            mor++;
                            avv_n--;
                            mor_n++;
                            if (coin == 0) { //gli capita la prudente
                                if (pru_n != 0 && coin2 == 0) {
                                    createFamily(Type.M, Type.P);
                                    mor_n--;
                                    pru_n--;
                                } else {
                                    mor_n--;
                                }
                            } else if (coin == 1) { //gli capita la spregiudicata
                                if (spr_n != 0 && coin2 == 0) {
                                    createFamily(Type.M, Type.S);
                                    mor_n--;
                                    spr_n--;
                                } else {
                                    mor_n--;
                                }
                            }
                        }
                        break;
                    }
                    case 2: { //prudente
                        int coin2 = r.nextInt(2);
                        boolean change_strategy = changeStrategy(Type.P);
                        if (!change_strategy) {
                            if (coin == 0) { //sceglie il morigerato
                                if (mor_n != 0 && coin2 == 0) {
                                    createFamily(Type.P, Type.M);
                                    pru_n--;
                                    mor_n--;
                                } else {
                                    pru_n--;
                                }
                            } else if (coin == 1) { //sceglie l'avventuriero
                                pru_n--;
                            }
                        } else { //cambia strategia (diventa spregiudicata)
                            pru--;
                            spr++;
                            pru_n--;
                            spr_n++;
                            if (coin == 0) { //gli capita il morigerato
                                if (mor_n != 0 && coin2 == 0) {
                                    createFamily(Type.S, Type.M);
                                    spr_n--;
                                    mor_n--;
                                } else {
                                    spr_n--;
                                }
                            } else if (coin == 1) { //gli capita l'avventuriero
                                if (avv_n != 0 && coin2 == 0) {
                                    createFamily(Type.S, Type.A);
                                    spr_n--;
                                    avv_n--;
                                } else {
                                    spr_n--;
                                }
                            }
                        }
                        break;
                    }
                    case 3: { //spregiudicata
                        int coin2 = r.nextInt(2);
                        boolean change_strategy = changeStrategy(Type.S);
                        if (!change_strategy) {
                            if (coin == 0) { //sceglie il morigerato
                                if (mor_n != 0 && coin2 == 0) {
                                    createFamily(Type.S, Type.M);
                                    spr_n--;
                                    mor_n--;
                                } else {
                                    spr_n--;
                                }
                            } else if (coin == 1) { //sceglie l'avventuriero
                                if (avv_n != 0 && coin2 == 0) {
                                    createFamily(Type.S, Type.A);
                                    spr_n--;
                                    avv_n--;
                                } else {
                                    spr_n--;
                                }
                            }
                        } else { //cambia strategia (diventa prudente)
                            spr--;
                            pru++;
                            spr_n--;
                            pru_n++;
                            if (coin == 0) { //gli capita il morigerato
                                if (mor_n != 0 && coin2 == 0) {
                                    createFamily(Type.P, Type.M);
                                    pru_n--;
                                    mor_n--;
                                } else {
                                    pru_n--;
                                }
                            } else if (coin == 1) { //gli capita l'avventuriero
                                pru_n--;
                            }
                        }
                        break;
                    }
                }
            }
            l.set(0,mor_n);
            l.set(1,avv_n);
            l.set(2,pru_n);
            l.set(3,spr_n);
            n = (n+1)%4;
        }
    }

    /**
     * Decide se un individuo deve o meno cambiare strategia analizzando i guadagni medi dei due tipi del suo sesso.
     * @param t Il tipo che deve decidere se cambiare o meno strategia
     * @return true se la cambia; false altrimenti
     */
    private boolean changeStrategy(Type t) {
        double perc_M = (double)mor/((double)mor+(double)avv);
        double perc_A = (double)avv/((double)mor+(double)avv);
        double perc_S = (double)spr/((double)spr+(double)pru);
        double perc_P = (double)pru/((double)spr+(double)pru);
        boolean ret = false;
        switch (t) {
            case M: {
                double guadagno_M = perc_P*(a-b/2-c)+perc_S*(a-b/2);
                double guadagno_A = perc_S*(a);
                if (guadagno_M < guadagno_A) ret = true;
                break;
            }
            case A: {
                double guadagno_M = perc_P*(a-b/2-c)+perc_S*(a-b/2);
                double guadagno_A = perc_S*(a);
                if (guadagno_A < guadagno_M) ret = true;
                break;
            }
            case P: {
                double guadagno_P = perc_M*(a-b/2-c);
                double guadagno_S = perc_M*(a-b/2)+perc_A*(a-b);
                if (guadagno_P < guadagno_S) ret = true;
                break;
            }
            case S: {
                double guadagno_P = perc_M*(a-b/2-c);
                double guadagno_S = perc_M*(a-b/2)+perc_A*(a-b);
                if (guadagno_S < guadagno_P) ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * Metodo per ottenere il 30% di un numero.
     * @param i il numero.
     * @return il 30% di i.
     */
    private int get30Percent(int i) {
        double d = ((double)i*30.00/100.00);
        if (d < 1) return i;
        return (int)d;
    }

    /**
     * Crea una famiglia. Ogni famiglia ha due figli che sono cloni dei genitori.
     * @param t1 tipo del primo genitore
     * @param t2 tipo del secondo genitore
     */
    public void createFamily(Type t1, Type t2) {
        switch (t1) {
            case A: {
                avv++;
                break;
            }
            case M: {
                mor++;
                break;
            }
            case P: {
                pru++;
                break;
            }
            case S: {
                spr++;
                break;
            }
        }
        switch (t2) {
            case A: {
                avv++;
                break;
            }
            case M: {
                mor++;
                break;
            }
            case P: {
                pru++;
                break;
            }
            case S: {
                spr++;
                break;
            }
        }
    }

    @Override
    public void setValues(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean isNextTo(int[] first, int[] other) {
        if (Math.abs((double)first[0]/((double)first[0]+(double)first[1]) - (double)other[0]/((double)other[0]+(double)other[1])) < 0.01) {
            if (Math.abs((double)first[1]/((double)first[0]+(double)first[1]) - (double)other[1]/((double)other[0]+(double)other[1])) < 0.01) {
                if (Math.abs((double)first[2]/((double)first[2]+(double)first[3]) - (double)other[2]/((double)other[2]+(double)other[3])) < 0.01) {
                    if (Math.abs((double)first[3]/((double)first[2]+(double)first[3]) - (double)other[3]/((double)other[2]+(double)other[3])) < 0.01) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkForStability(List<int[]> l) {
        for (int i = 1; i < l.size(); i++) {
            if (!isNextTo(l.get(0),l.get(i))) return false;
        }
        return true;
    }

    @Override
    public double getPercentage(Type t) throws IllegalArgumentException {
        int tot = mor+avv+spr+pru;
        switch (t) {
            case A: return Math.floor(((double)avv/(double)tot)*100 * 100) / 100;
            case M: return Math.floor(((double)mor/(double)tot)*100 * 100) / 100;
            case P: return Math.floor(((double)pru/(double)tot)*100 * 100) / 100;
            case S: return Math.floor(((double)spr/(double)tot)*100 * 100) / 100;
            default: return 0;
        }
    }

    /**
     * Metodo per incrementare la popolazione mentre il programma è in esecuzione. Serve a testare la stabilità.
     * @param i numero di indiviui da aggiungere
     * @param s tipo degli individui da aggiungere
     */
    public void incrementPopulation(int i, String s) {
        if (s.equals("M") || s.equals("m")) {
            mor += i;
        } else if (s.equals("P") || s.equals("p")) {
            pru += i;
        } else if (s.equals("S") || s.equals("s")) {
            spr += i;
        } else {
            avv += i;
        }
    }

    /**
     * Metodo che calcola la popolazione attuale
     * @return un array di interi con la popolaione attuale
     */
    public int[] currentPopulation() {
        int[] n = new int[4];
        n[0] += mor; n[1] += avv; n[2] += pru; n[3] += spr;
        return n;
    }
}
