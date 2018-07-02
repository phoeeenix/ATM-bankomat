package programpierwszybank;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;

public class ProgramPierwszyBankBH extends JFrame 
{
    public ProgramPierwszyBank()
    {
        initComponents();
    }
    
    JPanel panelGlowny = new JPanel();
    JPanel panelPomocniczy = new JPanel();
    JPanel panelButtonow = new JPanel();
    JPanel panelOperacji = new JPanel();
    JLabel komunikat = new JLabel();
    JLabel powitanie = new JLabel("Chcesz trochę $$$ ? Wybierz opcję.");
    JButton przyciskSaldo = new JButton("Sprawdź saldo");
    JButton przyciskWplata = new JButton("Wpłacam");
    JButton przyciskWyplata = new JButton("Wypłacam");
    JButton przyciskProbny = new JButton("Probny");
    double saldoStan = 100;
    JDesktopPane szybkaPulpitu = new JDesktopPane();
    JTextField obszarTekstowy = new JTextField();
    JTextField obszarTekstowyOperacji = new JTextField();
    JInternalFrame ramkaWewnetrzna = new JInternalFrame("", true, true, true, true);
    
    JDialog oknoDialogoweWyplaty = new JDialog();
    JDialog oknoDialogoweWplaty = new JDialog();
    int polozenieOsiX = Toolkit.getDefaultToolkit().getScreenSize().width/4;
    int polozenieOsiY = Toolkit.getDefaultToolkit().getScreenSize().height/4;
    int szerokoscRamkiGlownej = Toolkit.getDefaultToolkit().getScreenSize().width/2;
    int wysokoscRamkiGlownej = Toolkit.getDefaultToolkit().getScreenSize().height/2;
    
    
    public void initComponents()
    {
        this.setTitle("BANKOMAT");
        this.setBounds(polozenieOsiX, polozenieOsiY, szerokoscRamkiGlownej, wysokoscRamkiGlownej);
        this.setDefaultCloseOperation(3);
        this.getContentPane().add(panelGlowny);
        this.getContentPane().add(panelButtonow);
        this.getContentPane().add(panelPomocniczy);
      
        panelGlowny.add(powitanie);
        panelButtonow.add(przyciskSaldo);
        panelButtonow.add(przyciskWplata);
        panelButtonow.add(przyciskWyplata);
        panelPomocniczy.add(komunikat);
        panelGlowny.add(szybkaPulpitu);
        szybkaPulpitu.add(przyciskProbny);
        szybkaPulpitu.setVisible(true);
        JSplitPane podzialPaneli1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelGlowny, panelButtonow);
        JSplitPane podzialPaneli2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, podzialPaneli1, panelPomocniczy);
        this.getContentPane().add(podzialPaneli2);

      
        przyciskSaldo.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                komunikat.setText("Twoje saldo wynosi " + saldoStan + " zł.");
            }
        });
        
        przyciskWyplata.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                JInternalFrame ramkaWewnetrznaWyplata = new JInternalFrame("", true, true, true, true);

                oknoDialogoweWyplaty.setBounds(polozenieOsiX*3/2, polozenieOsiY*3/2, szerokoscRamkiGlownej/2, wysokoscRamkiGlownej/2);
                oknoDialogoweWyplaty.add(ramkaWewnetrznaWyplata);
                JLabel komunikatWyplaty = new JLabel ("Wpisz kwotę jaką chcesz wypłacić.");
                ramkaWewnetrznaWyplata.add(komunikatWyplaty, BorderLayout.NORTH);
                ramkaWewnetrznaWyplata.add(obszarTekstowy);
                obszarTekstowy.requestFocus();
                oknoDialogoweWyplaty.setVisible(true);
                ramkaWewnetrznaWyplata.setVisible(true);

                obszarTekstowy.addKeyListener(new KeyAdapter() 
                {  
                    @Override
                    public void keyTyped(KeyEvent ke) 
                    {
                        if (!czyJestCyfra(ke.getKeyChar()))
                            ke.consume();
                    }
                });

                obszarTekstowy.addKeyListener(new KeyAdapter() 
                {
                    @Override
                    public void keyPressed(KeyEvent ke)
                    {
                        if (ke.getKeyCode() == KeyEvent.VK_ENTER)
                        {
                            {
                                String odpowiedzWyplata = obszarTekstowy.getText().toString();
                                System.out.println("odpowiedzWyplata");
                                double kwotaWyplata = Double.parseDouble(odpowiedzWyplata);
                                if (kwotaWyplata <= saldoStan)
                                {
                                    double noweSaldo = saldoStan - kwotaWyplata;
                                    komunikat.setText("Obecnie Twoje saldo wynosi: " + noweSaldo + " zł.");
                                    setSaldoStan(noweSaldo);
                                }
                                else
                                    komunikat.setText("Za mało środków na koncie. Maksymalna wartość jaką możesz wypłacić to: " + saldoStan + " zł.");
                                obszarTekstowy.selectAll();
                                obszarTekstowy.replaceSelection("");
                                ramkaWewnetrznaWyplata.setVisible(false);
                                oknoDialogoweWyplaty.setVisible(false);
                            } 
                        }
                    }
                });
            }
        });
        
            przyciskWplata.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent ae) 
                {
                JInternalFrame ramkaWewnetrznaWplata = new JInternalFrame("", true, true, true, true);
                oknoDialogoweWplaty.setBounds(polozenieOsiX*3/2, polozenieOsiY*3/2, szerokoscRamkiGlownej/2, wysokoscRamkiGlownej/2);
                oknoDialogoweWplaty.add(ramkaWewnetrznaWplata);
                JLabel komunikatWplaty = new JLabel ("Wpisz kwotę jaką chcesz wpłacić.");
                ramkaWewnetrznaWplata.add(komunikatWplaty, BorderLayout.NORTH);
                ramkaWewnetrznaWplata.add(obszarTekstowyOperacji);
                obszarTekstowyOperacji.requestFocus();
                oknoDialogoweWplaty.setVisible(true);
                ramkaWewnetrznaWplata.setVisible(true);
                
                obszarTekstowyOperacji.addKeyListener(new KeyAdapter()
                { 
                    @Override
                    public void keyTyped(KeyEvent ke) 
                    {
                        if (!czyJestCyfra(ke.getKeyChar()))
                            ke.consume();
                    }
                });

                obszarTekstowyOperacji.addKeyListener(new KeyAdapter() 
                {
                    @Override
                    public void keyPressed(KeyEvent ke)
                    {
                        if (ke.getKeyCode() == KeyEvent.VK_ENTER)
                        {
                            String odpowiedzWplata = obszarTekstowyOperacji.getText().toString(); 
                            System.out.println("odpowiedzWplata");
                            double kwotaWplata = Double.parseDouble(odpowiedzWplata);
                            double noweSaldo = saldoStan + kwotaWplata;
                            komunikat.setText("Obecnie Twoje saldo wynosi: " + noweSaldo + " zł.");
                            setSaldoStan(noweSaldo);
                            obszarTekstowyOperacji.selectAll();
                            obszarTekstowyOperacji.replaceSelection("");
                            ramkaWewnetrznaWplata.setVisible(false);
                            oknoDialogoweWplaty.setVisible(false);
                        }
                    }
                });

                }
            });
    }
    
    public double setSaldoStan (double kwota)
    {
       return saldoStan = kwota;
    }
    
    public double getSaldoStan()
    {
        return saldoStan;
    }
    
       boolean czyJestCyfra(char znak)
        {
            if (znak >= '0' && znak <= '9')
                return true;
                return false;
        }
    
                                    
  public static void main(String[] args) 
  {
      new ProgramPierwszyBank().setVisible(true);
  }
    
}
