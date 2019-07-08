package NeuralNetwork;

import MnistRepository.RepositoryMnist;

import java.util.Arrays;

public class NeuralNetwork {
  private double learningRate;
  private InputLayer inputLayer;
  private OutputLayer outputLayer;
  private int[] hiddenLayers;
  private HiddenLayer[] layersOfNN;
  private RepositoryMnist db;


  /**
   * Конструктор,
   * надо перенести learningRate в метод тренировки сети, убрать его из конструктора и CreateNeuralNetwork
   **/

  public NeuralNetwork(double learningRate, int[] hiddenLayers) {
    this.learningRate = learningRate;
    this.hiddenLayers = hiddenLayers;
    CreateNeuralNetwork();
  }

  /**
   * Скрытый метод который по факту и созадает архитектуру нейронной сети
   **/

  private void CreateNeuralNetwork() {

    this.inputLayer = new InputLayer();

    this.layersOfNN = new HiddenLayer[hiddenLayers.length+1];
    for (int i = 0; i < hiddenLayers.length; i++) {
      if (i == 0) {
        layersOfNN[i] = new HiddenLayer(hiddenLayers[i], inputLayer, learningRate);
      } else {
        layersOfNN[i] = new HiddenLayer(hiddenLayers[i], layersOfNN[i - 1], learningRate);
        layersOfNN[i - 1].setForwardLayer(layersOfNN[i]);
      }
    }
    layersOfNN[hiddenLayers.length] = new HiddenLayer(10,layersOfNN[hiddenLayers.length-1],learningRate); // Это добавил т.к. у меня outputLayer это по факту не нейрон
    layersOfNN[hiddenLayers.length-1].setForwardLayer(layersOfNN[hiddenLayers.length]);

    this.outputLayer = new OutputLayer();
    layersOfNN[hiddenLayers.length].setForwardLayer(outputLayer);
  }




  /**
   * Метод тренировки нейронной сети
   * Мы передаем в него базу для тренировки и количество эпох
   *
   **/
  public void trainNetwork(RepositoryMnist db, int epoch) {

    for (int x = 0; x < epoch; x++) {
      for (int imageIndex = 0; imageIndex < db.getSize(); imageIndex++) {

        inputLayer.setSignals(db.getImage(imageIndex));
        outputLayer.setLabel(db.getValue(imageIndex));


                layersOfNN[0].setSignals(inputLayer.GetOutputSignals());
                for (int i = 1; i < layersOfNN.length; i++) {
                  layersOfNN[i].setSignals(layersOfNN[i - 1].GetOutputSignals());
                }
                outputLayer.setSignals(layersOfNN[ layersOfNN.length - 1].GetOutputSignals());


//        for (HiddenLayer hiddenLayer : layersOfNN) {
//          hiddenLayer.setSignals(hiddenLayer.getBackLayer.GetOutputSignals());
//        }
//        outputLayer.setSignals(outputLayer.getBackLayer.GetOutputSignals());



        for (int j = layersOfNN.length; j > 0; j--) {
          layersOfNN[j].SetLayerErrors();
        }

        for (int k = layersOfNN.length; k > 0; k--) {
          layersOfNN[k].ChangeWeights();
        }

      }
    }
  }


  public void testNetwork(RepositoryMnist db,int imgIndex) {

    inputLayer.setSignals(db.getImage(imgIndex));
    outputLayer.setLabel(db.getValue(imgIndex));
    layersOfNN[0].setSignals(inputLayer.GetOutputSignals());

    for (int i = 1; i < layersOfNN.length; i++) {
      layersOfNN[i].setSignals(layersOfNN[i + 1].GetOutputSignals());
    }

    outputLayer.setSignals(layersOfNN[layersOfNN.length - 1].GetOutputSignals());

    String s = Arrays.toString(outputLayer.GetOutputSignals());
    System.out.print("Результат по тестированию: " + s);

  }

}


