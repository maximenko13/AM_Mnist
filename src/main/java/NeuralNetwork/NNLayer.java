package NeuralNetwork;

interface NNLayer {
  //void ReCalcPropagationErrors();  //убрать из интерфейса
  double[] GetErrors();
  double[] GetOutputSignals();
  int GetLength();
  //  void setForwardLayer(NNLayer forwardLayer); //убрать из интерфейса
  void setSignals(double[] signals);
}
